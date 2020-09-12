package it.parttimeteam.model.game

import it.parttimeteam.core.GameInterface
import it.parttimeteam.core.cards.Card
import it.parttimeteam.gamestate.PlayerGameState
import it.parttimeteam.messages.GameMessage.{LeaveGame, PlayerActionMade, Ready}
import it.parttimeteam.model.ErrorEvent
import it.parttimeteam.model.game.RemoteGameActor.MatchServerResponseListener
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
                      private val notifyEvent: GameEvent => Unit,
                      private val gameInterface: GameInterface) extends GameService {

  private var turnHistory: History[PlayerGameState] = History[PlayerGameState]()
  private var storeOpt: Option[GameStateStore] = None

  implicit val executionContext: ExecutionContextExecutor = ActorSystemManager.actorSystem.dispatcher

  private val remoteMatchGameRef = gameInformation.gameRef
  private val playerId = gameInformation.playerId

  private val matchServerResponseListener = new MatchServerResponseListener {

    override def gameStateUpdated(gameState: PlayerGameState): Unit = {

      storeOpt match {
        case Some(store) => notifyEvent(StateUpdatedEvent(generateClientGameState(
          store.onStateChanged(gameState), turnHistory)))
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

  override def leaveGame(): Unit = this.remoteMatchGameRef ! LeaveGame(this.playerId)

  override def endTurnDrawingACard(): Unit = remoteMatchGameRef ! PlayerActionMade(this.playerId, DrawCard)

  override def endTurnWithMoves(): Unit = {
    withState { currentState =>
      if (isTurnValid(currentState)) {
        remoteMatchGameRef ! PlayerActionMade(this.playerId, PlayedMove(currentState.hand, currentState.board))
      }
      else {
        this.notifyEvent(GameErrorEvent(ErrorEvent.NoValidTurnPlay))
      }
    }
  }

  override def makeCombination(cards: Seq[Card]): Unit = {
    withState { state =>
      this.gameInterface.playCombination(state.hand, state.board, cards) match {
        case Right((updatedHand, updatedBoard)) =>
          val updatedState = storeOpt.get.onLocalTurnStateChanged(updatedHand, updatedBoard)
          this.updateHistoryAndNotify(updatedState)

        case Left(error) => this.notifyEvent(GameErrorEvent(ErrorEvent.mapError(error)))
      }

    }
  }

  override def pickCardsFromBoard(cards: Seq[Card]): Unit = {
    withState { currentState =>
      gameInterface.pickBoardCards(currentState.hand, currentState.board, cards) match {
        case Right((hand, board)) => {
          val updatedState = storeOpt.get.onLocalTurnStateChanged(hand, board)
          this.updateHistoryAndNotify(updatedState)
        }
        case Left(error) => this.notifyEvent(GameErrorEvent(ErrorEvent.mapError(error)))
      }
    }
  }

  override def updateCardCombination(combinationId: String, cards: Seq[Card]): Unit = {
    withState { state =>
      this.gameInterface.putCardsInCombination(state.hand, state.board, combinationId, cards) match {
        case Right((updatedHand, updatedBoard)) => {
          val updatedState = this.storeOpt.get.onLocalTurnStateChanged(updatedHand, updatedBoard)
          this.updateHistoryAndNotify(updatedState)
        }
        case Left(error) => this.notifyEvent(GameErrorEvent(ErrorEvent.mapError(error)))
      }
    }
  }

  override def undoTurn(): Unit = {
    this.updateStateThroughHistory(turnHistory.previous)
  }

  override def redoTurn(): Unit = {
    this.updateStateThroughHistory(turnHistory.next)
  }

  override def resetTurnState(): Unit = {
    this.updateStateThroughHistory(turnHistory.reset)
  }

  override def pickCardCombination(combinationId: String): Unit = {
    withState { state =>
      this.gameInterface.pickBoardCards(
        state.hand, state.board, state.board.combinations.find(_.id == combinationId).get.cards) match {
        case Right((updatedHand, updatedBoard)) => {
          val updatedState = this.storeOpt.get.onLocalTurnStateChanged(updatedHand, updatedBoard)
          this.updateHistoryAndNotify(updatedState)
        }
        case Left(error) => this.notifyEvent(GameErrorEvent(ErrorEvent.mapError(error)))
      }
    }
  }

  override def sortHandByRank(): Unit = {
    withState { state =>
      val updatedState = this.storeOpt.get.onLocalTurnStateChanged(state.hand.sortByRank(), state.board)
      this.updateHistoryAndNotify(updatedState)
    }
  }

  override def sortHandBySuit(): Unit = {
    withState { state =>
      val updatedState = this.storeOpt.get.onLocalTurnStateChanged(state.hand.sortBySuit(), state.board)
      this.updateHistoryAndNotify(updatedState)
    }
  }

  // endregion

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

  private def generateClientGameState(state: PlayerGameState,
                                      turnHistory: History[PlayerGameState]): ClientGameState =
    ClientGameState(
      playerGameState = state,
      canUndo = turnHistory.canPrevious,
      canRedo = turnHistory.canNext,
      canReset = turnHistory.canPrevious,
      canDrawCard = !isTurnValid(state)
    )

  /**
   * Execute the history method, updates the history and the resulting state
   *
   * @param historyUpdate History method to execute.
   */
  private def updateStateThroughHistory(historyUpdate: () => (Option[PlayerGameState], History[PlayerGameState])): Unit = {
    val (optState, updatedHistory) = historyUpdate()
    this.turnHistory = updatedHistory
    if (optState.isDefined) {
      this.storeOpt.get.onStateChanged(optState.get)
      this.notifyEvent(StateUpdatedEvent(generateClientGameState(optState.get, turnHistory)))
    }
  }

  /**
   * Return if Turn is valid or not.
   *
   * @param currentState Current Game State.
   * @return True if the Turn isn't valid, false anywhere.
   */
  private def isTurnValid(currentState: PlayerGameState) = {
    val startState = turnHistory.headOption
    startState.isDefined && gameInterface.validateTurn(
      currentState.board, startState.get.board, currentState.hand, startState.get.hand)
  }
}