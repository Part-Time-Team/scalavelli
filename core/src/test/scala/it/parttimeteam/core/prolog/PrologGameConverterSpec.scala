package it.parttimeteam.core.prolog

import alice.tuprolog.{Prolog, Term}
import it.parttimeteam.core.cards.{Card, Color, Rank, Suit}
import it.parttimeteam.core.prolog.converter.{PrologConverter, PrologGameConverter}
import org.scalatest.funsuite.AnyFunSuite

class PrologGameConverterSpec extends AnyFunSuite {

  val gameConverter: PrologGameConverter = PrologConverter()
  val prolog: Prolog = new Prolog

  test("Convert list of cards to string") {

    val card1: Card = Card(Rank.Ace(), Suit.Clubs(), Color.Red())
    val card2: Card = Card(Rank.Four(), Suit.Spades(), Color.Blue())
    val card3: Card = Card(Rank.King(), Suit.Diamonds(), Color.Red())

    assert(gameConverter.cardsConvert(Seq(card1, card2, card3)) equals "([(1,Clubs),(4,Spades),(13,Diamonds)]).")
  }

  test("Convert term to boolean") {

    val term1: Term = prolog toTerm "[1]"
    val term2: Term = prolog toTerm "[2]"

    assert(gameConverter.toBoolean(Seq(term1, term2)) equals true)
  }

  test("Convert term to string and replace character ' ") {

    val term: Term = prolog toTerm "['1']"

    assert(gameConverter.toStringAndReplace(term) equals "[1]")
  }
}
