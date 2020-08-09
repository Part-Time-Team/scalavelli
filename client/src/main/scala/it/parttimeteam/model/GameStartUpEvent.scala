package it.parttimeteam.model

import it.parttimeteam.model.startup.GameMatchInformations

sealed class GameStartUpEvent

case object LobbyJoinedEvent extends GameStartUpEvent

case class PrivateLobbyCreatedEvent(privateCode: String) extends GameStartUpEvent

case class GameStartedEvent(gameInfo: GameMatchInformations) extends GameStartUpEvent

case class LobbyJoinErrorEvent(result: String) extends GameStartUpEvent


