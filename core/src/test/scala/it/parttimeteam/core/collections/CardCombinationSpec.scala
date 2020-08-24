package it.parttimeteam.core.collections

import it.parttimeteam.core.cards
import it.parttimeteam.core.cards.{Color, Rank, Suit}
import org.scalatest.funspec.AnyFunSpec

class CardCombinationSpec extends AnyFunSpec {

  describe("A combination") {
    val card1 = cards.Card(Rank.Three(), Suit.Spades(), Color.Red())
    val card2 = cards.Card(Rank.Four(), Suit.Spades(), Color.Blue())
    val card3 = cards.Card(Rank.Five(), Suit.Spades(), Color.Blue())
    describe("Is invalid") {
      it("When is composed by less than 3 cards") {
        val comb = CardCombination(card1 +: (card2 +: Nil))
        assert(!comb.isValid)
      }
    }

    describe("Is valid") {
      it("When is composed by 3 consecutive cards") {
        val comb = CardCombination(card1 +: (card2 +: (card3 +: Nil)))
        assert(comb.isValid)
      }
    }
  }
}
