package it.parttimeteam.Prolog

import alice.tuprolog.{Prolog, Term, Var}
import it.parttimeteam.{Card, Rank, Suit}
import it.parttimeteam.Prolog.converter.PrologGameConverter
import it.parttimeteam.Prolog.engine.{PrologGameEngine, PrologStruct}
import org.scalatest.funsuite.AnyFunSuite

class PrologGameConverterSpec extends AnyFunSuite{

  test("Convert term to int"){

    val engine : PrologGameEngine = new PrologGameEngine
    val gameConverter: PrologGameConverter = new PrologGameConverter
    val numberCard :List[Term] = engine goal PrologStruct("startHand", new Var("X"))

    assert(gameConverter.toInt(numberCard.head) equals 13)
  }

  test("Convert list of cards to string"){

    val card1: Card = Card(Rank.Ace(), Suit.Clubs())
    val card2: Card = Card(Rank.Four(), Suit.Spades())
    val card3: Card = Card(Rank.King(), Suit.Diamonds())
    val gameConverter: PrologGameConverter = new PrologGameConverter

    assert(gameConverter.cardsConvert(List(card1, card2, card3)) equals "([(1,Clubs),(4,Spades),(13,Diamonds)]).")

  }

  test("Convert term to boolean"){

    val prolog : Prolog = new Prolog()
    val term1 : Term = prolog toTerm "[1]"
    val term2 : Term = prolog toTerm "[2]"
    val gameConverter: PrologGameConverter = new PrologGameConverter

    assert(gameConverter.toBoolean(List(term1, term2)) equals true)
  }

  test("Convert term to string and replace character '"){

    val prolog : Prolog = new Prolog()
    val term : Term = prolog toTerm "['1']"
    val gameConverter: PrologGameConverter = new PrologGameConverter

    assert(gameConverter.toStringAndReplace(term) equals "[1]")
  }
}
