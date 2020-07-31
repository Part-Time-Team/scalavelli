package it.parttimeteam

import akka.actor.ActorRef

case class GamePlayer(override val id: String,
                      username: String,
                      override val actorRef: ActorRef) extends Player
