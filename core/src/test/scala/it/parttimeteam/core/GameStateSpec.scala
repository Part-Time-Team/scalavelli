package it.parttimeteam.core

import it.parttimeteam.core.cards.Card
import it.parttimeteam.core.collections.{Board, Deck, Hand}
import it.parttimeteam.core.player.Player
import org.scalatest.funspec.AnyFunSpec

class GameStateSpec extends AnyFunSpec {

  describe("A GameState") {
    val card1 = Card.string2card("A♥R")
    val card2 = Card.string2card("4♦B")
    val card3 = Card.string2card("K♣B")
    val card4 = Card.string2card("Q♠R")

    var player1 = Player("Lorenzo", "1", Hand(List(card1)))
    val player2 = Player("Matteo", "2", Hand(List(card2)))
    val player3 = Player("Daniele", "3", Hand(List(card3)))
    val player4 = Player("Luca", "4", Hand(List(card4)))
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