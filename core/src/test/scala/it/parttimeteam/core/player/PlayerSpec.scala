package it.parttimeteam.core.player

import it.parttimeteam.core.collections.Hand
import org.scalatest.funspec.AnyFunSpec

class PlayerSpec extends AnyFunSpec{
  describe("A player") {
    describe("Made empty"){
      val player = Player.EmptyPlayer()
      it("Have a name") {
        assert(player.getName equals "Empty player")
      }

      it("Have an id") {
        assert(player.getId equals "Empty player")
      }

      it("Have an hand") {
        assert(player.hand equals Hand(List(), List()))
      }

      it("Can be converted to a string"){
        assert(player.toString() equals "Empty player")
      }
    }
    describe("Made full"){
      val player = Player.FullPlayer("Lorenzo", "#001", Hand(List(), List()))
      it("Have a name") {
        assert(player.getName equals "Name: Lorenzo")
      }

      it("Have an id") {
        assert(player.getId nonEmpty)
      }

      it("Have an hand") {
        assert(player.hand equals Hand(List(), List()))
      }
    }
  }

  /*test("Update Hand of the player") {
    val card1 : Card = Card(Rank.Eight(), Suit.Clubs())
    val card2 : Card = Card(Rank.Seven(), Suit.Spades())
    val player = Player.FullPlayer("Lorenzo", "#d502", Hand(List(), List()))

    assert(player refreshHand(List(card1), List(card2)) equals Hand(List(card1), List(card2)))
  }*/
}
