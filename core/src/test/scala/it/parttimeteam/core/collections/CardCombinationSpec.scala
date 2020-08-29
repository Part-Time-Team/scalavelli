package it.parttimeteam.core.collections

import it.parttimeteam.core.cards.{Card, Color, Rank, Suit}
import org.scalatest.funspec.AnyFunSpec

class CardCombinationSpec extends AnyFunSpec {

  describe("A combination") {
    val c1 = Card(Rank.Three(), Suit.Spades(), Color.Red())
    val c2 = Card(Rank.Four(), Suit.Spades(), Color.Blue())
    val c3 = Card(Rank.Five(), Suit.Spades(), Color.Blue())
    val c4 = Card(Rank.Five(), Suit.Diamonds(), Color.Blue())
    val c5 = Card(Rank.Five(), Suit.Clubs(), Color.Blue())

    it("Is created ordered") {
      val comb = CardCombination("#1", Seq(c1, c2, c3))
      assertResult(Seq(c1, c2, c3))(comb.cards)
    }

    describe("Is invalid") {
      it("When is composed by less than 3 cards") {
        val comb = CardCombination("#1", c1 +: (c2 +: Nil))
        assert(!comb.isValid)
      }

      it("Contains 3 unconsecutive cards or not same-rank cards") {
        val comb = CardCombination("#2", Seq(c1, c3, c5))
        assert(!comb.isValid)
      }

      it("Contains 3 chair cards and 3 tris cards") {
        val comb = CardCombination("#3", Seq(c1, c2, c3, c4, c5))
        assert(!comb.isValid)
      }
    }

    describe("Is valid") {
      it("When is composed by 3 consecutive cards") {
        val comb = CardCombination("#2", c1 +: (c2 +: (c3 +: Nil)))
        assert(comb.isValid)
      }
      it("When is composed by 3 same-rank cards") {
        val comb = CardCombination("#2", Seq(c3, c4, c5))
        assert(comb.isValid)
      }
    }

    describe("Modify cards") {
      var comb = CardCombination("#2", Seq(c3, c1, c2))
      it("Add cards") {
        assertResult(Seq(c1, c2, c3))(comb.cards)
        assert(comb.isValid)
        comb = comb.putCards(Seq(c4, c5))
        assertResult(Seq(c1, c2, c3, c4, c5))(comb.cards)
        assert(!comb.isValid)
      }

      it("Pick cards") {
        comb = comb.pickCards(Seq(c1, c2))
        assertResult(Seq(c3, c4, c5))(comb.cards)
        assert(comb.isValid)
      }
    }
  }
}
