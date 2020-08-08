package it.partitimeteam

import akka.actor.ActorSystem
import it.partitimeteam.lobby.LobbyManagerActor

object ScalavelliServer {
  def main(args: Array[String]): Unit = {

    val system = ActorSystem("ScalavelliServer")
    system.actorOf(LobbyManagerActor.props(), "lobby")

  }
}


