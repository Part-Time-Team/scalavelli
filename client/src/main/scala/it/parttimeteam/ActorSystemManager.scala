package it.parttimeteam

import akka.actor.ActorSystem

object ActorSystemManager {

  lazy val actorSystem: ActorSystem = ActorSystem()

}
