package it.parttimeteam.model.startup

sealed class GameStartupEvent

case object LobbyJoinedEvent extends GameStartupEvent

case class PrivateLobbyCreatedEvent(privateCode: String) extends GameStartupEvent

case class GameStartedEvent(gameInfo: GameMatchInformations) extends GameStartupEvent

case class LobbyJoinErrorEvent(result: String) extends GameStartupEvent


