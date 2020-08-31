package it.parttimeteam.common

import akka.actor.ActorRef

trait Referable {

  def actorRef: ActorRef

}
