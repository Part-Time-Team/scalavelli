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
  val history: History[PlayerGameState] = ???
  val store: GameStateStore = ???

  implicit val executionContext: ExecutionContextExecutor = ActorSystemManager.actorSystem.dispatcher

  private val remoteMatchGameRef = gameInformation.gameRef
  private val playerId = gameInformation.playerId

  private val gameClientActorRef = ActorSystemManager.actorSystem.actorOf(RemoteGameActor.props(new MatchServerResponseListener {
    override def gameStateUpdated(gameState: PlayerGameState): Unit = notifyEvent(StateUpdatedEvent(gameState))

    override def turnStarted(): Unit = {}

    override def turnEnded(): Unit = ???

    override def opponentInTurn(opponentName: String): Unit = ???

    override def turnEndedWithCartDrawn(card: Card): Unit = ???

    override def gameWon(): Unit = ???

    override def gameLost(winnerName: String): Unit = ???
  }))

  override def playerReady(): Unit = {
    // timer to be sure the view is ready on server responses
    ActorSystemManager.actorSystem.scheduler.scheduleOnce(1.second) {
      remoteMatchGameRef ! Ready(playerId, gameClientActorRef)
    }

  }

  override def notifyUserAction(action: UserGameAction): Unit = {

  }


}
