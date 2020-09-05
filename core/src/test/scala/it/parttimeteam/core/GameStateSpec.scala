package it.parttimeteam.core

import it.parttimeteam.core.TestCards._
import it.parttimeteam.core.collections.{Board, Deck, Hand}
import it.parttimeteam.core.player.Player
import org.scalatest.funspec.AnyFunSpec

class GameStateSpec extends AnyFunSpec {

  describe("A GameState") {

    var player1 = Player("Lorenzo", "1", Hand(List(ACE_HEARTS)))
    val player2 = Player("Matteo", "2", Hand(List(FOUR_DIAMONDS)))
    val player3 = Player("Daniele", "3", Hand(List(KING_CLUBS)))
    val player4 = Player("Luca", "4", Hand(List(QUEEN_SPADES)))
    val list = List(player1, player2, player3, player4)

    val game = GameState(Deck.shuffled, Board(Nil), list)
    var fakePlayer = Player("Andrea", "5", Hand())

    describe("Can get an Option of the player") {
      it("If the player is present") {
        val p = game getPlayer player1.id
        assert(p contains player1)
      }

      it("If player is not present") {
        val p = game getPlayer fakePlayer.id
        assert(p.isEmpty)
      }
    }

    describe("Can update a player") {
      it("Can update a player if is already present") {
        player1 = player1.copy(name = "Davide")
        assert((game updatePlayer player1 getPlayer player1.id).get.name == "Davide")
      }

      it("Dosen't update an unexsisting player") {
        fakePlayer = fakePlayer.copy(name = "Davide")
        val updated = game updatePlayer fakePlayer
        assert((updated getPlayer fakePlayer.id).isEmpty)
      }
    }

    describe("can determine if a player won") {
      val winnerPlayer = Player("Winner", "5", Hand())
      val game2 = GameState(Deck.shuffled, Board(Nil), winnerPlayer :: list)
      it("return true if the player has an empty hand") {
        assert(game2.playerWon(winnerPlayer.id))
      }

      it("return false if the player has an empty hand") {
        assert(!game2.playerWon(player1.id))
      }

    }

  }
}
