package it.parttimeteam.lobby

import it.partitimeteam.common.GamePlayer
import it.partitimeteam.lobby.{LobbyManagerImpl, PlayerNumberLobby}
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

  }


}
