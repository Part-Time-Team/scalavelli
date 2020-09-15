package it.parttimeteam.model.startup

import it.parttimeteam.model.ErrorEvent

sealed class GameStartupEvent

case object LobbyJoinedEvent extends GameStartupEvent

case class PrivateLobbyCreatedEvent(privateCode: String) extends GameStartupEvent

case class GameStartedEvent(gameInfo: GameMatchInformations) extends GameStartupEvent

case class LobbyJoinErrorEvent(error: ErrorEvent) extends GameStartupEvent


