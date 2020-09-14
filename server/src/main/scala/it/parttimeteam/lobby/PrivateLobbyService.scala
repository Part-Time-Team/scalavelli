package it.parttimeteam.lobby

object PrivateLobbyService {
  def apply(): PrivateLobbyService = new PrivateLobbyServiceImpl()
}


/**
 * Manages the creation of private lobbies
 */
trait PrivateLobbyService {

  /**
   *
   * @param numberOfPlayers number of player
   * @return the
   */
  def generateNewPrivateLobby(numberOfPlayers: Int): PrivateLobby

  def retrieveExistingLobby(lobbyId: String): Option[PrivateLobby]

  def removeLobby(lobbyId: String): Unit

}


