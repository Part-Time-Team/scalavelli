package it.parttimeteam.Prolog

import alice.tuprolog.{Prolog, Term, Var}
import it.parttimeteam.{Card, Rank, Suit}
import it.parttimeteam.Prolog.converter.{PrologConverter, PrologGameConverter}
import it.parttimeteam.Prolog.engine.{PrologEngine, PrologGameEngine, PrologStruct}
import org.scalatest.funsuite.AnyFunSuite

class PrologGameConverterSpec extends AnyFunSuite {

  val gameConverter: PrologGameConverter = PrologConverter()
  val prolog: Prolog = new Prolog

  test("Convert term to int") {

    val engine: PrologGameEngine = PrologEngine()
    val numberCard: List[Term] = engine goal PrologStruct("startHand", new Var("X"))

    assert(gameConverter.toInt(numberCard.head) equals 13)
  }

  test("Convert list of cards to string") {

    val card1: Card = Card(Rank.Ace(), Suit.Clubs())
    val card2: Card = Card(Rank.Four(), Suit.Spades())
    val card3: Card = Card(Rank.King(), Suit.Diamonds())

    assert(gameConverter.cardsConvert(List(card1, card2, card3)) equals "([(1,Clubs),(4,Spades),(13,Diamonds)]).")
  }

  test("Convert term to boolean") {

    val term1: Term = prolog toTerm "[1]"
    val term2: Term = prolog toTerm "[2]"

    assert(gameConverter.toBoolean(List(term1, term2)) equals true)
  }

  test("Convert term to string and replace character ' ") {

    val term: Term = prolog toTerm "['1']"

    assert(gameConverter.toStringAndReplace(term) equals "[1]")
  }
}
