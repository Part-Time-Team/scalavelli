package it.parttimeteam

import akka.actor.ActorRef

trait Referable {

  def actorRef: ActorRef

}
