package it.parttimeteam.lobby

import it.parttimeteam.common.GamePlayer
import org.scalamock.scalatest.MockFactory
import org.scalatest.wordspec.AnyWordSpecLike

class LobbyManagerSpec extends AnyWordSpecLike with MockFactory {

  "a LobbyManager" should {

    "create a lobby on a new player insertion" in {
      val lobbyManager = new LobbyManagerImpl[GamePlayer]()
      val player = mock[GamePlayer]
      val lobbyType = PlayerNumberLobby(4)
      lobbyManager.addPlayer(player, lobbyType)
      assert(lobbyManager.getLobby(lobbyType).isDefined)
    }

    "return a previously added lobby" in {
      val lobbyManager = LobbyManager()
      val lobbyType = PlayerNumberLobby(4)
      lobbyManager.addPlayer(mock[GamePlayer], lobbyType)
      assert(lobbyManager.getLobby(lobbyType).isDefined)
    }

    "return nothing if no lobby has been added" in {
      val lobbyManager = LobbyManager()
      val lobbyType = PlayerNumberLobby(4)
      assert(lobbyManager.getLobby(lobbyType).isEmpty)
    }

    "return nothing on trying to create a match with no added players" in {
      val lobbyManager = LobbyManager()
      val lobbyType = PlayerNumberLobby(2)
      assert(lobbyManager.attemptExtractPlayerForMatch(lobbyType).isEmpty)
    }

    "return the necessary player (previously added) to start a match" in {
      val lobbyManager = LobbyManager()
      val lobbyType = PlayerNumberLobby(2)
      lobbyManager.addPlayer(mock[GamePlayer], lobbyType)
      lobbyManager.addPlayer(mock[GamePlayer], lobbyType)
      assert(lobbyManager.attemptExtractPlayerForMatch(lobbyType).isDefined)
    }

    "return nothing on the second attempt of returning players if are not enough" in {
      val lobbyManager = LobbyManager()
      val lobbyType = PlayerNumberLobby(2)
      lobbyManager.addPlayer(mock[GamePlayer], lobbyType)
      lobbyManager.addPlayer(mock[GamePlayer], lobbyType)
      assert(lobbyManager.attemptExtractPlayerForMatch(lobbyType).isDefined)
      assert(lobbyManager.attemptExtractPlayerForMatch(lobbyType).isEmpty)
    }


    "remove from lobby" in {
      val lobbyManager = LobbyManager()
      val lobbyType = PlayerNumberLobby(2)
      val p1 = GamePlayer("1", "pippo", null)
      val p2 = GamePlayer("2", "pluto", null)
      lobbyManager.addPlayer(p1, lobbyType)
      lobbyManager.removePlayer(p1.id)
      lobbyManager.addPlayer(p1, lobbyType)

      assert(lobbyManager.attemptExtractPlayerForMatch(lobbyType).isEmpty)

      lobbyManager.addPlayer(p2, lobbyType)
      assert(lobbyManager.attemptExtractPlayerForMatch(lobbyType).isDefined)

    }


  }


}
