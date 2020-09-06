package it.parttimeteam.model.game

import it.parttimeteam.controller.game._
import it.parttimeteam.core.cards.Card
import it.parttimeteam.core.{GameInterface, GameInterfaceImpl}
import it.parttimeteam.gamestate.PlayerGameState
import it.parttimeteam.messages.GameMessage.{LeaveGame, PlayerActionMade, Ready}
import it.parttimeteam.model.startup.GameMatchInformations
import it.parttimeteam.{ActorSystemManager, DrawCard, PlayedMove}

import scala.concurrent.ExecutionContextExecutor
import scala.concurrent.duration._

class GameServiceImpl(private val gameInformation: GameMatchInformations,
                      private val notifyEvent: ServerGameEvent => Unit) extends GameService {

  private var turnHistory: History[PlayerGameState] = History[PlayerGameState]()
  private var storeOpt: Option[GameStateStore] = None

  private val gameInterface: GameInterface = new GameInterfaceImpl()

  implicit val executionContext: ExecutionContextExecutor = ActorSystemManager.actorSystem.dispatcher

  private val remoteMatchGameRef = gameInformation.gameRef
  private val playerId = gameInformation.playerId

  private val gameClientActorRef = ActorSystemManager.actorSystem.actorOf(RemoteGameActor.props(new MatchServerResponseListener {
    override def gameStateUpdated(gameState: PlayerGameState): Unit = {

      storeOpt match {
        case Some(store) => notifyEvent(StateUpdatedEvent(generateClientGameState(store.onStateChanged(gameState), turnHistory)))
        case None =>
          storeOpt = Some(GameStateStore(gameState))
          notifyEvent(StateUpdatedEvent(generateClientGameState(gameState, turnHistory)))
      }
    }

    override def turnStarted(): Unit = {
      withState { state =>
        turnHistory = turnHistory.setPresent(state)
        notifyEvent(StateUpdatedEvent(generateClientGameState(state, turnHistory)))
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

  }), "client-game")


  // region GameService

  override def playerReady(): Unit = {
    // timer to be sure the view is ready on server responses
    ActorSystemManager.actorSystem.scheduler.scheduleOnce(1.second) {
      remoteMatchGameRef ! Ready(playerId, gameClientActorRef)
    }
  }

  override def notifyUserAction(action: UserGameAction): Unit = {
    val currentState = this.storeOpt.get.currentState

    action match {

      case EndTurnAndDrawAction =>
        remoteMatchGameRef ! PlayerActionMade(this.playerId, DrawCard)
      case EndTurnAction => {
        remoteMatchGameRef ! PlayerActionMade(this.playerId, PlayedMove(currentState.hand, currentState.board))
      }

      case MakeCombinationAction(cards) => {
        withState { state =>
          this.gameInterface.playCombination(state.hand, state.board, cards) match {
            case Right((updatedHand, updatedBoard)) =>
              val updatedState = storeOpt.get.onLocalTurnStateChanged(updatedHand, updatedBoard)
              this.turnHistory = this.turnHistory.setPresent(updatedState)
              this.notifyEvent(StateUpdatedEvent(generateClientGameState(updatedState, turnHistory)))

            case Left(error) => this.notifyEvent(ErrorEvent(error))
          }

        }
      }

      case PickCardsAction(cards) => {
        gameInterface.pickBoardCards(currentState.hand, currentState.board, cards) match {
          case Right((hand, board)) => {
            val updatedState = storeOpt.get.onLocalTurnStateChanged(hand, board)
            this.turnHistory = this.turnHistory.setPresent(updatedState)
            this.notifyEvent(StateUpdatedEvent(generateClientGameState(updatedState, turnHistory)))
          }
          case Left(error) => this.notifyEvent(ErrorEvent(error))
        }

      }

      case UpdateCardCombinationAction(combinationId, card) => {

      }

      case UndoAction => {
        val (optState, updatedHistory) = turnHistory.previous()
        this.turnHistory = updatedHistory
        if (optState.isDefined) {
          this.storeOpt.get.onStateChanged(optState.get)
          this.notifyEvent(StateUpdatedEvent(generateClientGameState(optState.get, turnHistory)))
        }
      }

      case RedoAction => {
        val (optState, updatedHistory) = turnHistory.next()
        this.turnHistory = updatedHistory
        if (optState.isDefined) {
          this.storeOpt.get.onStateChanged(optState.get)
          this.notifyEvent(StateUpdatedEvent(generateClientGameState(optState.get, turnHistory)))
        }
      }

      case ResetAction => {
        val (optState, updatedHistory) = turnHistory.reset()
        this.turnHistory = updatedHistory
        if (optState.isDefined) {
          this.storeOpt.get.onStateChanged(optState.get)
          this.notifyEvent(StateUpdatedEvent(generateClientGameState(optState.get, turnHistory)))
        }
      }

      case LeaveGameAction => this.remoteMatchGameRef ! LeaveGame(this.playerId)

      case SortHandByRankAction =>
      case SortHandBySuitAction =>
      case PickCardCombinationAction(combinationId: String) =>
      case _ =>

    }
  }

  // endregion

  private def withState(f: PlayerGameState => Unit): Unit = this.storeOpt match {
    case Some(state) => f(state.currentState)
    case _ =>
  }

  private def generateClientGameState(state: PlayerGameState, turnHistory: History[PlayerGameState]): ClientGameState = {
    turnHistory.printAll()
    ClientGameState(state, turnHistory.canPrevious, turnHistory.canNext, turnHistory.canPrevious, !turnHistory.canPrevious)
  }

}
