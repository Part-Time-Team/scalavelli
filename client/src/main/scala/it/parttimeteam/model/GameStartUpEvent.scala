package it.parttimeteam.model

import akka.actor.ActorRef

sealed class GameStartUpEvent {

}

case class LobbyJoinedEvent(userId: String) extends GameStartUpEvent
case class PrivateLobbyCreated(userId: String, privateCode: String) extends GameStartUpEvent
case class GameStarted(gameActorRef: ActorRef) extends GameStartUpEvent


