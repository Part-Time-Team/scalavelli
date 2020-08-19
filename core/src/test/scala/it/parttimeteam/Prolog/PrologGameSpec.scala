package it.parttimeteam.Prolog

import it.parttimeteam.{Card, Rank, Suit}
import org.scalatest.funsuite.AnyFunSuite

class PrologGameSpec extends AnyFunSuite {
  val prologGame: PrologGame = PrologGame()

  test("Load deck") {
    assert(prologGame.loadDeck().size equals 104)
  }

  test("Check number of initial cards") {
    assert(prologGame.startGameHand() equals 13)
  }

  test("Validate a valid quarter") {

    val card1: Card = Card(Rank.Ace(), Suit.Clubs())
    val card2: Card = Card(Rank.Ace(), Suit.Spades())
    val card3: Card = Card(Rank.Ace(), Suit.Diamonds())

    assert(prologGame.validateQuarter(List(card1, card2, card3)) equals true)
  }

  test("Validate an invalid quarter") {

    val card1: Card = Card(Rank.Ace(), Suit.Clubs())
    val card2: Card = Card(Rank.Ace(), Suit.Spades())
    val card3: Card = Card(Rank.Ace(), Suit.Spades())

    assert(prologGame.validateQuarter(List(card1, card2, card3)) equals false)
  }

  test("Validate a valid sequence") {

    val card1: Card = Card(Rank.Ace(), Suit.Clubs())
    val card2: Card = Card(Rank.Two(), Suit.Clubs())
    val card3: Card = Card(Rank.Three(), Suit.Clubs())
    val card4: Card = Card(Rank.Four(), Suit.Clubs())

    assert(prologGame.validateSequence(List(card1, card2, card3, card4)) equals true)
  }

  test("Validate an invalid sequence") {

    val card1: Card = Card(Rank.Ace(), Suit.Clubs())
    val card2: Card = Card(Rank.Two(), Suit.Clubs())
    val card3: Card = Card(Rank.Four(), Suit.Clubs())
    val card4: Card = Card(Rank.Five(), Suit.Clubs())

    assert(prologGame.validateSequence(List(card1, card2, card3, card4)) equals false)
  }
}
