package it.parttimeteam.model

import akka.actor.ActorRef

sealed class GameStartUpEvent {

}

case class LobbyJoinedEvent(userId: String) extends GameStartUpEvent

case class PrivateLobbyCreatedEvent(userId: String, privateCode: String) extends GameStartUpEvent

case class GameStartedEvent(gameActorRef: ActorRef) extends GameStartUpEvent

case class LobbyJoinErrorEvent(result: String) extends GameStartUpEvent


