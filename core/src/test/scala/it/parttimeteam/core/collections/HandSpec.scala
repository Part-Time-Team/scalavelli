package it.parttimeteam.core.collections

import it.parttimeteam.core.cards
import it.parttimeteam.core.cards.{Card, Color, Rank, Suit}
import org.scalatest.funsuite.AnyFunSuite

class HandSpec extends AnyFunSuite{

  test("Check add cards to the list playerCard"){
    val card1: Card = cards.Card(Rank.Ace(), Suit.Clubs(), Color.Red())
    val card2: Card = cards.Card(Rank.Four(), Suit.Spades(), Color.Red())
    val card3: Card = cards.Card(Rank.King(), Suit.Diamonds(), Color.Blue())

    val hand: Hand = Hand(List(card1))
    assert(hand.addPlayerCards(card2, card3) equals Hand(List(card1, card2, card3)))
  }

  test("Check add cards to the list tableCard"){
    val card1: Card = cards.Card(Rank.Ace(), Suit.Clubs(), Color.Blue())
    val card2: Card = cards.Card(Rank.Four(), Suit.Spades(), Color.Red())
    val card3: Card = cards.Card(Rank.King(), Suit.Diamonds(), Color.Red())
    val card4: Card = cards.Card(Rank.Queen(), Suit.Diamonds(), Color.Blue())

    val hand: Hand = Hand(List(card4), List(card1))
    assert(hand.addTableCards(card3, card2) equals Hand(List(card4), List(card1, card3, card2)))
  }

  test("Get player's hand"){
    val card1: Card = cards.Card(Rank.Ace(), Suit.Clubs(), Color.Blue())
    val card2: Card = cards.Card(Rank.Four(), Suit.Spades(), Color.Blue())

    val hand : Hand = Hand(List(card1, card2), List())
    assert(hand.getHand equals Hand(List(card1, card2), List()))
  }

  test("Sort cards by value"){
    val card1: Card = cards.Card(Rank.Ace(), Suit.Clubs(), Color.Blue())
    val card2: Card = cards.Card(Rank.Four(), Suit.Spades(), Color.Red())
    val card3: Card = cards.Card(Rank.King(), Suit.Diamonds(), Color.Blue())
    val card4: Card = cards.Card(Rank.Queen(), Suit.Diamonds(), Color.Blue())

    val hand : Hand = Hand(List(), List())
    assert(hand.sortByValue(card3, card4, card1, card2) equals List(card1, card2, card4, card3))
  }

  test("Sort cards by suit"){
    val card1: Card = cards.Card(Rank.Two(), Suit.Clubs(), Color.Red())
    val card2: Card = cards.Card(Rank.Four(), Suit.Spades(), Color.Red())
    val card3: Card = cards.Card(Rank.King(), Suit.Hearts(), Color.Blue())
    val card4: Card = cards.Card(Rank.Queen(), Suit.Diamonds(), Color.Red())

    val hand : Hand = Hand(List(), List())
    assert(hand.sortBySuit(card1, card2, card3, card4) equals List(card3, card4, card1, card2))
  }
}
