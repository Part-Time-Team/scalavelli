package it.parttimeteam.core.prolog

import alice.tuprolog.{Prolog, Term}
import it.parttimeteam.core.cards.{Card, Color, Rank, Suit}
import it.parttimeteam.core.prolog.converter.{PrologConverter, PrologGameConverter}
import org.scalatest.funsuite.AnyFunSuite

class PrologGameConverterSpec extends AnyFunSuite {

  val gameConverter: PrologGameConverter = new PrologGameConverter
  val prolog: Prolog = new Prolog

  test("Convert list of cards to string") {

    val card1: Card = Card(Rank.Ace(), Suit.Clubs(), Color.Red())
    val card2: Card = Card(Rank.Four(), Suit.Spades(), Color.Blue())
    val card3: Card = Card(Rank.King(), Suit.Diamonds(), Color.Red())

    assert(gameConverter.cardsConvertToString(Seq(card1, card2, card3))(None) equals "([(1,Clubs),(4,Spades),(13,Diamonds)]).")
  }

  test("Convert term to boolean") {

    val term1: Term = prolog toTerm "[1]"
    val term2: Term = prolog toTerm "[2]"

    assert(gameConverter.resultToBoolean(Seq(term1, term2)) equals true)
  }

  test("Convert term to string and replace character ' ") {

    val term: Term = prolog toTerm "['1']"

    assert(gameConverter.resultToStringAndReplace(term, "'") equals "[1]")
  }

  test("Change optional value of the card Ace if it is after King card"){

    val card1: Card = Card(Rank.Queen(), Suit.Clubs(), Color.Red())
    val card2: Card = Card(Rank.King(), Suit.Clubs(), Color.Blue())
    val card3: Card = Card(Rank.Ace(), Suit.Clubs(), Color.Red())
    val card4: Card = Card(Rank.Two(), Suit.Clubs(), Color.Blue())
    val card5: Card = Card(Rank.Three(), Suit.Clubs(), Color.Red())

    assertResult(Seq(card1, card2, card3.copy(rank = 14)))(gameConverter.optionalValueCards(Seq(card1, card2, card3)))
    assertResult(Seq(card1, card2, card3.copy(rank = 14), card4, card5))(gameConverter.optionalValueCards(Seq(card1, card2, card3, card4, card5)))
    assertResult(Seq(card3, card4, card5))(gameConverter.optionalValueCards(Seq(card3, card4, card5)))
  }
}
