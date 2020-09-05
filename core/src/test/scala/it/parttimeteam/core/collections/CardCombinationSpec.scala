package it.parttimeteam.core.collections

import it.parttimeteam.core.TestCards._
import org.scalatest.funspec.AnyFunSpec

class CardCombinationSpec extends AnyFunSpec {

  describe("A combination") {

    it("Is created ordered") {
      val comb = CardCombination("#1", Seq(THREE_CLUBS, FOUR_SPADES, FIVE_SPADES))
      assertResult(Seq(THREE_CLUBS, FOUR_SPADES, FIVE_SPADES))(comb.cards)
    }

    describe("Is invalid") {
      it("When is composed by less than 3 cards") {
        val comb = CardCombination("#1", THREE_CLUBS +: (FOUR_SPADES +: Nil))
        assert(!comb.isValid)
      }

      it("Contains 3 unconsecutive cards or not same-rank cards") {
        val comb = CardCombination("#2", Seq(THREE_CLUBS, FIVE_SPADES, FIVE_CLUBS))
        assert(!comb.isValid)
      }

      it("Contains 3 chair cards and 3 tris cards") {
        val comb = CardCombination("#3", Seq(THREE_CLUBS, FOUR_SPADES, FIVE_SPADES, FIVE_DIAMONDS, FIVE_CLUBS))
        assert(!comb.isValid)
      }
    }

    describe("Is valid") {
      it("When is composed by 3 consecutive cards") {
        val comb = CardCombination("#2", THREE_CLUBS +: (FOUR_SPADES +: (FIVE_SPADES +: Nil)))
        assert(comb.isValid)
      }
      it("When is composed by 3 same-rank cards") {
        val comb = CardCombination("#2", Seq(FIVE_SPADES, FIVE_DIAMONDS, FIVE_CLUBS))
        assert(comb.isValid)
      }
    }

    describe("Modify cards") {
      var comb = CardCombination("#2", Seq(FIVE_SPADES, THREE_CLUBS, FOUR_SPADES))
      it("Add cards") {
        assertResult(Seq(THREE_CLUBS, FOUR_SPADES, FIVE_SPADES))(comb.cards)
        assert(comb.isValid)
        comb = comb.putCards(Seq(FIVE_DIAMONDS, FIVE_CLUBS))
        assertResult(Seq(THREE_CLUBS, FOUR_SPADES, FIVE_SPADES, FIVE_DIAMONDS, FIVE_CLUBS))(comb.cards)
        assert(!comb.isValid)
      }

      it("Pick cards") {
        comb = comb.pickCards(Seq(THREE_CLUBS, FOUR_SPADES))
        assertResult(Seq(FIVE_SPADES, FIVE_DIAMONDS, FIVE_CLUBS))(comb.cards)
        assert(comb.isValid)
      }
    }
  }
}
