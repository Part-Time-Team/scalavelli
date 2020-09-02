package it.parttimeteam.lobby

import it.parttimeteam.common.{GamePlayer, Player}
import it.parttimeteam.lobby.{GameLobby, Lobby}
import org.scalamock.scalatest.MockFactory
import org.scalatest.wordspec.AnyWordSpecLike

class LobbySpec extends AnyWordSpecLike with MockFactory {

  "a lobby should" should {


    "create an empty lobby" in {

      val lobby = this.create2PlayersLobby

      assert(lobby.players.isEmpty)

    }

    "add a player to a lobby" in {
      val lobby = new GameLobby[Player](4)

      assert(lobby.players.isEmpty)

      val updatedLobby = lobby.addPlayer(mock[Player])

      assert(updatedLobby.players.length == 1)

    }

    "not have enough players if empty" in {
      val lobby = this.create2PlayersLobby
      assert(!lobby.hasEnoughPlayers)
    }

    "have enouth player after adding enough players" in {
      var lobby = create2PlayersLobby
      lobby = lobby.addPlayer(mock[Player])
      lobby = lobby.addPlayer(mock[Player])
      assert(lobby.hasEnoughPlayers)
    }

    "remove a previous added player" in {
      var lobby = create2PlayersLobby
      val playerName = "pippo"
      val player = GamePlayer(playerName, playerName, null)
      lobby = lobby.addPlayer(player)
      assert(lobby.players.length == 1)
      lobby = lobby.removePlayer(playerName)
      assert(lobby.players.isEmpty)
    }

    "extract player for match if players are enough" in {
      var lobby = create2PlayersLobby
      lobby = lobby.addPlayer(mock[Player])
      lobby = lobby.addPlayer(mock[Player])
      lobby = lobby.addPlayer(mock[Player])
      val result = lobby.extractPlayersForMatch()
      assert(result.second.isDefined && result.second.get.length == 2)
      assertResult(1)(result.first.players.length)
    }

    "return nothig on the second extraction player if players are not enough" in {
      var lobby = create2PlayersLobby
      lobby = lobby.addPlayer(mock[Player])
      lobby = lobby.addPlayer(mock[Player])
      lobby = lobby.addPlayer(mock[Player])
      val result = lobby.extractPlayersForMatch()
      assert(result.second.isDefined && result.second.get.length == 2)
      assertResult(1)(result.first.players.length)
      assert(result.first.extractPlayersForMatch().second.isEmpty)
    }


  }

  private def create2PlayersLobby: Lobby[Player] = {
    GameLobby(2)
  }

}
