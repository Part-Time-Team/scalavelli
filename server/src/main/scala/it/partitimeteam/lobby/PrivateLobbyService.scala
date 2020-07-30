package it.partitimeteam.lobby

/**
 * Manages the creation of private lobbies
 */
trait PrivateLobbyService {

  def generateNewPrivateLobby(numberOfPlayers: Int): PrivateLobby

  def retrieveExistingLobby(lobbyId: String): Option[PrivateLobby]

  def removeLobby(lobbyId: String): Unit

}


