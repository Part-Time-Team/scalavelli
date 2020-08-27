package it.parttimeteam.model.game

import it.parttimeteam.ActorSystemManager
import it.parttimeteam.core.cards.Card
import it.parttimeteam.core.{GameManager, GameManagerImpl}
import it.parttimeteam.gamestate.PlayerGameState
import it.parttimeteam.messages.GameMessage.{EndTurnAndDraw, EndTurnWithPlays, LeaveGame, Ready}
import it.parttimeteam.model.startup.GameMatchInformations
import it.parttimeteam.view.game._

import scala.concurrent.ExecutionContextExecutor
import scala.concurrent.duration._

class GameServiceImpl(private val gameInformation: GameMatchInformations,
                      private val notifyEvent: GameEvent => Unit) extends GameService {

  private var turnHistory: History[PlayerGameState] = _
  private var storeOpt: Option[GameStateStore] = None

  private val gameManager: GameManager = new GameManagerImpl()

  implicit val executionContext: ExecutionContextExecutor = ActorSystemManager.actorSystem.dispatcher

  private val remoteMatchGameRef = gameInformation.gameRef
  private val playerId = gameInformation.playerId

  private val gameClientActorRef = ActorSystemManager.actorSystem.actorOf(RemoteGameActor.props(new MatchServerResponseListener {

    override def gameStateUpdated(gameState: PlayerGameState): Unit = {

      storeOpt match {
        case Some(store) => notifyEvent(StateUpdatedEvent(store.onStateChanged(gameState)))
        case None =>
          storeOpt = Some(GameStateStore(gameState))
          notifyEvent(StateUpdatedEvent(gameState))
      }

    }

    override def turnStarted(): Unit = notifyEvent(InTurnEvent)

    override def turnEnded(): Unit = notifyEvent(TurnEndedEvent)

    override def opponentInTurn(opponentName: String): Unit = notifyEvent(OpponentInTurnEvent(opponentName))

    override def turnEndedWithCartDrawn(card: Card): Unit = {
      // TODO reset history

      notifyEvent(StateUpdatedEvent(storeOpt.get.onCardDrawn(card)))
      notifyEvent(TurnEndedEvent)

    }

    override def gameWon(): Unit = notifyEvent(GameWonEvent)

    override def gameLost(winnerName: String): Unit = notifyEvent(GameLostEvent(winnerName))

  }))


  // region GameService

  override def playerReady(): Unit = {
    // timer to be sure the view is ready on server responses
    ActorSystemManager.actorSystem.scheduler.scheduleOnce(1.second) {
      remoteMatchGameRef ! Ready(playerId, gameClientActorRef)
    }

  }

  override def notifyUserAction(action: GameViewEvent): Unit = {
    val currentState = this.storeOpt.get.currentState

    action match {
      case EndTurnAndDrawViewEvent => remoteMatchGameRef ! EndTurnAndDraw(this.playerId)
      case EndTurnViewEvent => {

        remoteMatchGameRef ! EndTurnWithPlays(this.playerId, currentState.board, currentState.hand)
      }
      case MakeCombinationViewEvent(cards) => {
        // validate and play
      }
      case PickCardsViewEvent(cards) => {
        gameManager.pickBoardCards(currentState.hand, currentState.board, cards) match {
          case Right((hand, board)) => {
            val updatedState = storeOpt.get.onLocalTurnStateChanged(hand, board)
            this.turnHistory = this.turnHistory.setPresent(updatedState)
            this.notifyEvent(StateUpdatedEvent(updatedState))
          }
        }

      }
      case UpdateCardCombinationViewEvent(combinationId, card, indexToAdd) => {

      }
      case UndoViewEvent => {
        val (optState, updatedHistory) = turnHistory.previous()
        this.turnHistory = updatedHistory
        if (optState.isDefined) {
          this.storeOpt.get.onStateChanged(optState.get)
          this.notifyEvent(StateUpdatedEvent(optState.get))
        }
      }
      case RedoViewEvent => {
        val (optState, updatedHistory) = turnHistory.next()
        this.turnHistory = updatedHistory
        if (optState.isDefined) {
          this.storeOpt.get.onStateChanged(optState.get)
          this.notifyEvent(StateUpdatedEvent(optState.get))
        }
      }
      case ResetHistoryViewEvent => {
        // TODO implement reset method on history
      }
      case LeaveGameViewEvent => this.remoteMatchGameRef ! LeaveGame(this.playerId)

      case SortHandByRankViewEvent =>
      case SortHandBySuitViewEvent =>

    }
  }


  // endregion


}
