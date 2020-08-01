package it.partitimeteam.lobby

import it.partitimeteam.common.IdGenerator

class PrivateLobbyServiceImpl extends PrivateLobbyService with IdGenerator {

  private var lobbies = Set[PrivateLobby]()

  override def generateNewPrivateLobby(numberOfPlayers: Int): PrivateLobby = {
    val lobby = PrivateLobby(generateId, numberOfPlayers)
    lobbies = lobbies + lobby
    lobby
  }

  override def retrieveExistingLobby(lobbyId: String): Option[PrivateLobby] =
    lobbies.find(l => l.lobbyId == lobbyId)

  override def removeLobby(lobbyId: String): Unit =
    lobbies = lobbies.filter(l => l.lobbyId != lobbyId)

}
