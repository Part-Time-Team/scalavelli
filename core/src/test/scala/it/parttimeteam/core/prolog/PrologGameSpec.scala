package it.parttimeteam.core.prolog

import it.parttimeteam.core.cards.{Card, Color, Rank, Suit}
import org.scalatest.funsuite.AnyFunSuite

class PrologGameSpec extends AnyFunSuite {
  val prologGame: PrologGame = PrologGame()

  test("Load deck") {
    val deck = prologGame.loadDeck
    assert(deck.size equals 104)
  }

  test("Validate a valid quarter") {

    val card1: Card = Card(Rank.Ace(), Suit.Clubs(), Color.Blue())
    val card2: Card = Card(Rank.Ace(), Suit.Spades(), Color.Blue())
    val card3: Card = Card(Rank.Ace(), Suit.Diamonds(), Color.Red())

    assert(prologGame.validateQuarter(List(card1, card2, card3)) equals true)
  }

  test("Validate an invalid quarter") {

    val card1: Card = Card(Rank.Ace(), Suit.Clubs(), Color.Red())
    val card2: Card = Card(Rank.Ace(), Suit.Spades(), Color.Red())
    val card3: Card = Card(Rank.Ace(), Suit.Spades(), Color.Blue())

    assert(prologGame.validateQuarter(List(card1, card2, card3)) equals false)
  }

  test("Validate a valid sequence") {

    val card1: Card = Card(Rank.Ace(), Suit.Clubs(), Color.Red())
    val card2: Card = Card(Rank.Two(), Suit.Clubs(), Color.Red())
    val card3: Card = Card(Rank.Three(), Suit.Clubs(), Color.Blue())
    val card4: Card = Card(Rank.Four(), Suit.Clubs(), Color.Red())

    assert(prologGame.validateSequence(List(card1, card2, card3, card4)) equals true)
  }

  test("Validate an invalid sequence") {

    val card1: Card = Card(Rank.Ace(), Suit.Clubs(), Color.Blue())
    val card2: Card = Card(Rank.Two(), Suit.Clubs(), Color.Blue())
    val card3: Card = Card(Rank.Four(), Suit.Clubs(), Color.Blue())
    val card4: Card = Card(Rank.Five(), Suit.Clubs(), Color.Red())

    assert(prologGame.validateSequence(List(card1, card2, card3, card4)) equals false)
  }
}
