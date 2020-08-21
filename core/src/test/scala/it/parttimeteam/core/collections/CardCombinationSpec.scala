package it.parttimeteam.core.collections

import it.parttimeteam.core.cards
import it.parttimeteam.core.cards.{Card, Color, Rank, Suit}
import org.scalatest.funsuite.AnyFunSuite

class CardCombinationSpec extends AnyFunSuite{

  test("Check a combination is valid"){

    val card1: Card = cards.Card(Rank.Ace(), Suit.Clubs(), Color.Red())
    val card2: Card = cards.Card(Rank.Four(), Suit.Spades(), Color.Blue())

    val cardCombination = CardCombination(List(card1, card2))
    assert(cardCombination.isValid equals true)
  }
}
