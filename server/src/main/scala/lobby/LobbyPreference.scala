package lobby

sealed class LobbyPreference

case class NumberOfPlayers(numberOfPlayers: Int) extends LobbyPreference

case class PrivateCode(lobbyId: String) extends LobbyPreference
