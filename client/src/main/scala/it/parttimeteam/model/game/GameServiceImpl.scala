package it.parttimeteam.model.game

import it.parttimeteam.ActorSystemManager
import it.parttimeteam.gamestate.PlayerGameState
import it.parttimeteam.messages.GameMessage.Ready
import it.parttimeteam.model.startup.GameMatchInformations

class GameServiceImpl(private val gameInformation: GameMatchInformations,
                      private val notifyEvent: GameEvent => Unit) extends GameService {


  private val remoteMatchGameRef = gameInformation.gameRef
  private val playerId = gameInformation.playerId

  private val gameClientActorRef = ActorSystemManager.actorSystem.actorOf(RemoteGameActor.props(new MatchServerResponseListener {
    override def onGameStateUpdated(gameState: PlayerGameState): Unit = notifyEvent(StateUpdatedEvent(gameState))

    override def onTurnStarted(): Unit = {}
  }))

  override def playerReady(): Unit = {
    remoteMatchGameRef ! Ready(playerId, gameClientActorRef)
  }

  override def notifyUserAction(action: UserGameAction): Unit = {

  }


}

/**
 * Used to notify about GameService events
 */
trait GameServiceListener {
  def onGameStateUpdated(state: PlayerGameState)
}
