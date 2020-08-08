package it.parttimeteam.model

import akka.actor.ActorRef

sealed class GameStartUpEvent {

}

case object LobbyJoinedEvent extends GameStartUpEvent

case class PrivateLobbyCreatedEvent(privateCode: String) extends GameStartUpEvent

case class GameStartedEvent(gameActorRef: ActorRef) extends GameStartUpEvent

case class LobbyJoinErrorEvent(result: String) extends GameStartUpEvent


