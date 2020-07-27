package it.parttimeteam

import org.scalatest.funsuite.AnyFunSuite

class HandSpec extends AnyFunSuite{

  test("Check add a one card to list playerCard"){
    val hand: Hand = Hand()
    val card: Card = Card(Rank.Ace(), Suit.Clubs())
    assert(hand.addPlayerCard(card) equals Hand(List(card)))
  }

  test("Check add a one card to list tableCard"){
    val hand: Hand = Hand()
    val card: Card = Card(Rank.Ace(), Suit.Clubs())
    assert(hand.addTableCard(card) equals Hand(List(), List(card)))
  }

  test("Check add a more card to list playerCard"){
    val card1: Card = Card(Rank.Ace(), Suit.Clubs())
    val card2: Card = Card(Rank.Four(), Suit.Spades())
    val card3: Card = Card(Rank.King(), Suit.Diamonds())

    val hand: Hand = Hand(List(card1))
    assert(hand.addPlayerCards(card2, card3) equals Hand(List(card1, card2, card3)))
  }

  test("Check add a more card to list tableCard"){
    val card1: Card = Card(Rank.Ace(), Suit.Clubs())
    val card2: Card = Card(Rank.Four(), Suit.Spades())
    val card3: Card = Card(Rank.King(), Suit.Diamonds())
    val card4: Card = Card(Rank.Queen(), Suit.Diamonds())

    val hand: Hand = Hand(List(card4), List(card1))
    assert(hand.addTableCards(card3, card2) equals Hand(List(card4), List(card1, card3, card2)))
  }
}
