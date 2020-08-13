package it.parttimeteam.model.startup

import akka.actor.ActorRef

case class GameMatchInformations(playerId: String, gameRef: ActorRef)
