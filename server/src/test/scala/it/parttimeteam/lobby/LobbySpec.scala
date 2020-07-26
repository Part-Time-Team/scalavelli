package it.parttimeteam.lobby

import it.partitimeteam.common.{Player, Referable}
import it.partitimeteam.lobby.{GameLobby, Lobby}
import org.scalamock.scalatest.MockFactory
import org.scalatest.wordspec.AnyWordSpecLike

class LobbySpec extends AnyWordSpecLike with MockFactory {

  "a lobby should" should {


    "create an empty lobby" in {

      val lobby = this.create2PlayersLobby

      assert(lobby.players.isEmpty)

    }

    "add a player to a lobby" in {

      val player = mock[Player]
      val lobby = new GameLobby[Player](4)

      assert(lobby.players.isEmpty)

      val updatedLobby = lobby.addPlayer("pippo", player)

      assert(updatedLobby.players.length == 1)

    }

    "not have enough players if empty" in {
      val lobby = this.create2PlayersLobby
      assert(!lobby.hasEnoughPlayers)
    }

    "have enouth player after adding enough players" in {
      var lobby = create2PlayersLobby
      lobby = lobby.addPlayer("pippo", mock[Player])
      lobby = lobby.addPlayer("pluto", mock[Player])
      assert(lobby.hasEnoughPlayers)
    }

    "remove a previous added player" in {
      var lobby = create2PlayersLobby
      val playerName = "pippo"
      val player = mock[Player]
      lobby = lobby.addPlayer(playerName, player)
      assert(lobby.players.length == 1)
      lobby = lobby.removePlayer(playerName)
      assert(lobby.players.isEmpty)
    }

    "extract player for match if players are enough" in {
      var lobby = create2PlayersLobby
      lobby = lobby.addPlayer("pippo", mock[Player])
      lobby = lobby.addPlayer("pluto", mock[Player])
      lobby = lobby.addPlayer("paperino", mock[Player])
      val result = lobby.extractPlayersForMatch()
      assert(result.second.isDefined && result.second.get.length == 2)
      assertResult(1)(result.first.players.length)
    }


  }

  private def create2PlayersLobby: Lobby[Player] = {
    GameLobby[Player](2)
  }

}
