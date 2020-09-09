package it.parttimeteam.model.game

import it.parttimeteam.controller.game._
import it.parttimeteam.core.GameInterface
import it.parttimeteam.core.cards.Card
import it.parttimeteam.gamestate.PlayerGameState
import it.parttimeteam.messages.GameMessage.{LeaveGame, PlayerActionMade, Ready}
import it.parttimeteam.model.startup.GameMatchInformations
import it.parttimeteam.{ActorSystemManager, DrawCard, PlayedMove}

import scala.concurrent.ExecutionContextExecutor
import scala.concurrent.duration._

/**
 *
 * @param gameInformation information user to participate to a game match
 * @param notifyEvent     function used to notify the external component about game updates
 * @param gameInterface   the game core api
 */
class GameServiceImpl(private val gameInformation: GameMatchInformations,
                      private val notifyEvent: ServerGameEvent => Unit,
                      private val gameInterface: GameInterface) extends GameService {

  private var turnHistory: History[PlayerGameState] = History[PlayerGameState]()
  private var storeOpt: Option[GameStateStore] = None

  implicit val executionContext: ExecutionContextExecutor = ActorSystemManager.actorSystem.dispatcher

  private val remoteMatchGameRef = gameInformation.gameRef
  private val playerId = gameInformation.playerId

  private val matchServerResponseListener = new MatchServerResponseListener {
    override def gameStateUpdated(gameState: PlayerGameState): Unit = {

      storeOpt match {
        case Some(store) => notifyEvent(StateUpdatedEvent(generateClientGameState(store.onStateChanged(gameState), turnHistory)))
        case None =>
          // initial state, initialize the game store
          storeOpt = Some(GameStateStore(gameState))
          notifyEvent(StateUpdatedEvent(generateClientGameState(gameState, turnHistory)))
      }
    }

    override def turnStarted(): Unit = {
      withState { state =>
        updateHistoryAndNotify(state)
      }
      notifyEvent(InTurnEvent)
    }

    override def turnEnded(): Unit = {
      turnHistory = turnHistory.clear()
      notifyEvent(TurnEndedEvent)
    }

    override def opponentInTurn(opponentName: String): Unit = notifyEvent(OpponentInTurnEvent(opponentName))

    override def turnEndedWithCartDrawn(card: Card): Unit = {

      turnHistory = turnHistory.clear()

      notifyEvent(StateUpdatedEvent(generateClientGameState(storeOpt.get.onCardDrawn(card), turnHistory)))
      notifyEvent(TurnEndedEvent)

    }

    override def gameWon(): Unit = notifyEvent(GameWonEvent)

    override def gameLost(winnerName: String): Unit = notifyEvent(GameLostEvent(winnerName))

    override def gameEndedWithErrorEvent(reason: String): Unit = notifyEvent(GameEndedWithErrorEvent(reason))

  }

  private val gameClientActorRef = ActorSystemManager.actorSystem.actorOf(RemoteGameActor.props(this.matchServerResponseListener))


  // region GameService

  override def playerReady(): Unit = {
    // timer to be sure the view is ready on server responses
    ActorSystemManager.actorSystem.scheduler.scheduleOnce(1.second) {
      remoteMatchGameRef ! Ready(playerId, gameClientActorRef)
    }
  }

  override def notifyUserAction(action: UserGameAction): Unit = {

    action match {

      case EndTurnAndDrawAction => this.endTurnDrawingACard()

      case EndTurnAction => this.endTurnWithMoves()

      case MakeCombinationAction(cards) => this.makeCombination(cards)

      case PickCardsAction(cards) => this.pickCardsFromBoard(cards)

      case UpdateCardCombinationAction(combinationId, cards) => this.updateCardCombination(combinationId, cards)

      case UndoAction => this.undoTurnAction()

      case RedoAction => this.redoTurnAction()

      case ResetAction => this.resetTurnState()

      case LeaveGameAction => this.leaveGame()

      case SortHandByRankAction => this.sortHandByRank()

      case SortHandBySuitAction => this.sortHandBySuit()

      case PickCardCombinationAction(combinationId: String) => this.pickCardCombination(combinationId)

      case _ =>

    }
  }

  // endregion

  private def leaveGame(): Unit = {
    this.remoteMatchGameRef ! LeaveGame(this.playerId)
  }

  private def endTurnDrawingACard(): Unit = {
    remoteMatchGameRef ! PlayerActionMade(this.playerId, DrawCard)
  }

  private def endTurnWithMoves(): Unit = {
    withState { currentState =>
      if (this.gameInterface.validateMove(currentState.board, currentState.hand)) {
        remoteMatchGameRef ! PlayerActionMade(this.playerId, PlayedMove(currentState.hand, currentState.board))
      } else {
        this.notifyEvent(ErrorEvent("Non valid turn play"))
      }
    }
  }

  private def makeCombination(cards: Seq[Card]): Unit = {
    withState { state =>
      this.gameInterface.playCombination(state.hand, state.board, cards) match {
        case Right((updatedHand, updatedBoard)) =>
          val updatedState = storeOpt.get.onLocalTurnStateChanged(updatedHand, updatedBoard)
          this.updateHistoryAndNotify(updatedState)

        case Left(error) => this.notifyEvent(ErrorEvent(error))
      }

    }
  }

  private def pickCardsFromBoard(cards: Seq[Card]): Unit = {
    withState { currentState =>
      gameInterface.pickBoardCards(currentState.hand, currentState.board, cards) match {
        case Right((hand, board)) => {
          val updatedState = storeOpt.get.onLocalTurnStateChanged(hand, board)
          this.updateHistoryAndNotify(updatedState)
        }
        case Left(error) => this.notifyEvent(ErrorEvent(error))
      }
    }
  }

  private def updateCardCombination(combinationId: String, cards: Seq[Card]): Unit = {
    withState { state =>
      val (updatedHand, updatedBoard) = this.gameInterface.putCardsInCombination(state.hand, state.board, combinationId, cards)
      val updatedState = this.storeOpt.get.onLocalTurnStateChanged(updatedHand, updatedBoard)
      this.updateHistoryAndNotify(updatedState)
    }
  }

  private def undoTurnAction(): Unit = {
    this.updateStateThroughHistory(turnHistory.previous)
  }

  private def redoTurnAction(): Unit = {
    this.updateStateThroughHistory(turnHistory.next)
  }

  private def resetTurnState(): Unit = {
    this.updateStateThroughHistory(turnHistory.reset)
  }

  private def pickCardCombination(combinationId: String): Unit = {
    withState { state =>
      this.gameInterface.pickBoardCards(state.hand, state.board, state.board.combinations.find(_.id == combinationId).get.cards) match {
        case Right((updatedHand, updatedBoard)) => {
          val updatedState = this.storeOpt.get.onLocalTurnStateChanged(updatedHand, updatedBoard)
          this.updateHistoryAndNotify(updatedState)
        }
        case Left(error) => this.notifyEvent(ErrorEvent(error))
      }
    }
  }

  private def sortHandByRank(): Unit = {
    withState { state =>
      val updatedState = this.storeOpt.get.onLocalTurnStateChanged(state.hand.sortByRank(), state.board)
      this.updateHistoryAndNotify(updatedState)
    }
  }

  private def sortHandBySuit(): Unit = {
    withState { state =>
      val updatedState = this.storeOpt.get.onLocalTurnStateChanged(state.hand.sortBySuit(), state.board)
      this.updateHistoryAndNotify(updatedState)
    }
  }


  private def updateHistoryAndNotify(updatedState: PlayerGameState): Unit = {
    this.updateHistory(updatedState)
    this.notifyEvent(StateUpdatedEvent(generateClientGameState(updatedState, turnHistory)))
  }

  private def updateHistory(newState: PlayerGameState): Unit = {
    this.turnHistory = this.turnHistory.setPresent(newState)
  }

  private def withState(f: PlayerGameState => Unit): Unit = this.storeOpt match {
    case Some(state) => f(state.currentState)
    case _ =>
  }

  private def generateClientGameState(state: PlayerGameState, turnHistory: History[PlayerGameState]): ClientGameState = {
    ClientGameState(state, turnHistory.canPrevious, turnHistory.canNext, turnHistory.canPrevious, !turnHistory.canPrevious)
  }

  /**
   * Execute the history method, updates the history and the resulting state
   *
   * @param historyUpdate
   */
  private def updateStateThroughHistory(historyUpdate: () => (Option[PlayerGameState], History[PlayerGameState])): Unit = {
    val (optState, updatedHistory) = historyUpdate()
    this.turnHistory = updatedHistory
    if (optState.isDefined) {
      this.storeOpt.get.onStateChanged(optState.get)
      this.notifyEvent(StateUpdatedEvent(generateClientGameState(optState.get, turnHistory)))
    }
  }


}
