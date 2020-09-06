package it.parttimeteam

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import it.parttimeteam.lobby.LobbyManagerActor

object ScalavelliServer extends App {
  // def main(args: Array[String]): Unit = {

    val config = ConfigFactory.parseString(
      s"""
         |akka.remote.netty.tcp.hostname = ${Constants.Remote.SERVER_ADDRESS}
         |akka.remote.netty.tcp.port = ${Constants.Remote.SERVER_PORT}
         |""".stripMargin).withFallback(ConfigFactory.load())

    val system = ActorSystem(Constants.Remote.SERVER_ACTOR_SYSTEM_NAME, config)
    system.actorOf(LobbyManagerActor.props(), Constants.Remote.SERVER_LOBBY_ACTOR_NAME)

  // }
}


