package it.parttimeteam.entities

import akka.actor.ActorRef

trait Referable {

  def actorRef: ActorRef

}
