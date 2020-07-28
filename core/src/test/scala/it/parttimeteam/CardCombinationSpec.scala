package it.parttimeteam

import org.scalatest.funsuite.AnyFunSuite

class CardCombinationSpec extends AnyFunSuite{

  test("Check a combination is valid"){

    val card1: Card = Card(Rank.Ace(), Suit.Clubs())
    val card2: Card = Card(Rank.Four(), Suit.Spades())

    val cardCombination = CardCombination(List(card1, card2))
    assert(cardCombination.isValidate equals true)
  }
}
