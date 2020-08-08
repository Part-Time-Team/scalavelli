package it.partitimeteam

import akka.actor.ActorSystem
import it.partitimeteam.lobby.LobbyManagerActor
import it.parttimeteam.Constants

object ScalavelliServer {
  def main(args: Array[String]): Unit = {

    val system = ActorSystem(Constants.Remote.SERVER_ACTOR_SYSTEM_NAME)
    system.actorOf(LobbyManagerActor.props(), Constants.Remote.SERVER_LOBBY_ACTOR_NAME)

  }
}


