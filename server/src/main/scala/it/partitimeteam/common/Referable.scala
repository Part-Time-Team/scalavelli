package it.partitimeteam.common

import akka.actor.ActorRef

trait Referable {

  def getActorRef: ActorRef

}
