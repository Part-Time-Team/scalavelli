package it.parttimeteam.model.game

import it.parttimeteam.ActorSystemManager
import it.parttimeteam.entities.GamePlayerState
import it.parttimeteam.model.startup.GameMatchInformations

class GameServiceImpl(private val gameInformation: GameMatchInformations,
                      private val lobbyServiceListener: GameServiceListener) extends GameService {


  private val remoteMatchGameRef = gameInformation.gameRef
  private val playerId = gameInformation.playerId

  private val gameClientActorRef = ActorSystemManager.actorSystem.actorOf(RemoteGameActor.props(new MatchServerResponseListener {
    override def onGameStateUpdated(gameState: GamePlayerState): Unit = ???

    override def onTurnStarted(): Unit = ???
  }))

  override def notifyUserAction(action: UserGameAction): Unit = {

  }


}

/**
 * Used to notify about GameService events
 */
trait GameServiceListener {

}
