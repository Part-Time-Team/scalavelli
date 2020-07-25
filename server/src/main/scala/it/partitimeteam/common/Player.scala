package it.partitimeteam.common

import akka.actor.ActorRef

case class Player(name: String, actorRef: ActorRef) extends Referable {
  override def getActorRef: ActorRef = actorRef
}
