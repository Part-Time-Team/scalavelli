package it.parttimeteam.model.game

import it.parttimeteam.ActorSystemManager
import it.parttimeteam.core.cards.Card
import it.parttimeteam.gamestate.PlayerGameState
import it.parttimeteam.messages.GameMessage.Ready
import it.parttimeteam.model.startup.GameMatchInformations

import scala.concurrent.ExecutionContextExecutor
import scala.concurrent.duration._

class GameServiceImpl(private val gameInformation: GameMatchInformations,
                      private val notifyEvent: GameEvent => Unit) extends GameService {

  private var turnHistory: History[PlayerGameState] = _
  private var storeOpt: Option[GameStateStore] = None

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

    override def turnStarted(): Unit = {
    }

    override def turnEnded(): Unit = ???

    override def opponentInTurn(opponentName: String): Unit = notifyEvent(OpponentInTurnEvent(opponentName))

    override def turnEndedWithCartDrawn(card: Card): Unit = {
      // TODO reset history

      notifyEvent(StateUpdatedEvent(storeOpt.get.onCardDrawn(card)))

    }

    override def gameWon(): Unit = ???

    override def gameLost(winnerName: String): Unit = ???

  }))


  // region GameService

  override def playerReady(): Unit = {
    // timer to be sure the view is ready on server responses
    ActorSystemManager.actorSystem.scheduler.scheduleOnce(1.second) {
      remoteMatchGameRef ! Ready(playerId, gameClientActorRef)
    }

  }

  override def notifyUserAction(action: UserGameAction): Unit = {

  }

  // endregion

}
