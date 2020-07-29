package it.partitimeteam.lobby

sealed class LobbyType(val numberOfPlayers: Int)

case class PlayerNumberLobby(override val numberOfPlayers: Int) extends LobbyType(numberOfPlayers)

case class PrivateLobby(lobbyId: String, override val numberOfPlayers: Int) extends LobbyType(numberOfPlayers)
