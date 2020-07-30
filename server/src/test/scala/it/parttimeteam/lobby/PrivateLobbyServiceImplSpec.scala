package it.parttimeteam.lobby

import it.partitimeteam.lobby.PrivateLobbyServiceImpl
import org.scalatest.wordspec.AnyWordSpecLike

class PrivateLobbyServiceImplSpec extends AnyWordSpecLike {

  private val RANDOM_PLAYER_NUMBER = 4

  "a private lobby service" should {

    "create a new private lobby" in {
      val lobbyService = new PrivateLobbyServiceImpl()
      val lobby = lobbyService.generateNewPrivateLobby(RANDOM_PLAYER_NUMBER)
      assertResult(RANDOM_PLAYER_NUMBER)(lobby.numberOfPlayers)
    }

    "retrieve a previously created lobby" in {
      val lobbyService = new PrivateLobbyServiceImpl()
      val lobby = lobbyService.generateNewPrivateLobby(RANDOM_PLAYER_NUMBER)
      assertResult(Some(lobby))(lobbyService.retrieveExistingLobby(lobby.lobbyId))
    }

    "not found a non existing private lobby" in {
      val lobbyService = new PrivateLobbyServiceImpl()
      assertResult(None)(lobbyService.retrieveExistingLobby("fakeId"))
    }

    "remove a previously created lobby" in {
      val lobbyService = new PrivateLobbyServiceImpl()
      val lobby = lobbyService.generateNewPrivateLobby(RANDOM_PLAYER_NUMBER)
      lobbyService.removeLobby(lobby.lobbyId)
      assertResult(None)(lobbyService.retrieveExistingLobby(lobby.lobbyId))
    }

  }

}
