package it.parttimeteam.core.collections

import it.parttimeteam.core.cards
import it.parttimeteam.core.cards.{Card, Rank, Suit}
import org.scalatest.funsuite.AnyFunSuite

class CardCombinationSpec extends AnyFunSuite{

  test("Check a combination is valid"){

    val card1: Card = cards.Card(Rank.Ace(), Suit.Clubs())
    val card2: Card = cards.Card(Rank.Four(), Suit.Spades())

    val cardCombination = CardCombination(List(card1, card2))
    assert(cardCombination.isValidate equals true)
  }
}
