package it.partitimeteam.lobby

sealed class LobbyType

case class PlayerNumberLobby(numberOfPlayers: Int) extends LobbyType

case class PrivateLobby(lobbyId: String) extends LobbyType
