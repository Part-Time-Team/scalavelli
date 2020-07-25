package it.parttimeteam.lobby

import it.partitimeteam.common.{Player, Referable}
import it.partitimeteam.lobby.GameLobby
import org.scalamock.scalatest.MockFactory
import org.scalatest.wordspec.AnyWordSpecLike

class LobbySpec extends AnyWordSpecLike with MockFactory {

  "a lobby should" should {

    "create an empty lobby" in {

      val player = mock[Player]

      val lobby = new GameLobby[Player](4)

      assert(lobby.players.isEmpty)

    }

    "add a player to a lobby" in {

      val player = mock[Player]

      val lobby = new GameLobby[Player](4)

      assert(lobby.players.isEmpty)

      val updatedLobby = lobby.addPlayer("pippo", player)

      assert(updatedLobby.players.length == 1)

    }

  }

}
