package it.parttimeteam.core.prolog.converter

import alice.tuprolog.{Prolog, Term, Var}
import it.parttimeteam.core.cards.{Card, Color, Rank, Suit}
import it.parttimeteam.core.TestCards._
import it.parttimeteam.core.prolog.engine.PrologGameEngine
import org.scalatest.funsuite.AnyFunSuite

class PrologGameConverterSuite extends AnyFunSuite {

  val prologConverter: PrologGameConverter = new PrologGameConverter
  val prolog: Prolog = new Prolog

  test("Convert sequence of cards in string") {

    val sequence: Seq[Card] = Seq(ACE_CLUBS_BLUE, FOUR_SPADES, KING_DIAMONDS)

    assertResult("([(1,\"C\",\"B\"),(4,\"S\",\"B\"),(13,\"D\",\"R\")]).")(prologConverter.cardsConvertToString(sequence)(None))
    assertResult("([(1,\"C\",\"B\"),(4,\"S\",\"B\"),(13,\"D\",\"R\")],X).")(prologConverter.cardsConvertToString(sequence)(Some(new Var("X"))))
  }

  test("Convert a sequence of term into a tuple sequence") {

    val prologEngine = new PrologGameEngine
    val sequence: Seq[Card] = Seq(ACE_CLUBS_BLUE, ACE_SPADES, ACE_DIAMONDS)

    val termSeq: Seq[Term] = prologEngine goal "quicksortValue" + prologConverter.cardsConvertToString(sequence)(Some(new Var("X")))
    val tupleSeq: Seq[Card] = sequence map (card => Card(card.rank, card.suit, card.color))

    assertResult(tupleSeq)(prologConverter.sortedCard(termSeq))
  }

  test("Get a card given the color, rank and suit") {
    val card: Card = Card(Rank.Ace(), Suit.Clubs(), Color.Blue())

    assertResult(card)(prologConverter getCard(prolog toTerm "B", prolog toTerm "C", prolog toTerm "1"))
  }

  test("It replaces the Ace value card in the Overflow Ace card in specific cases") {

    assertResult(Seq(QUEEN_CLUBS, KING_CLUBS, ACE_CLUBS_BLUE.copy(rank = "14")))(prologConverter.optionalValueAce(Seq(QUEEN_CLUBS, KING_CLUBS, ACE_CLUBS_BLUE)))

    assertResult(Seq(QUEEN_CLUBS, KING_CLUBS, ACE_CLUBS_BLUE.copy(rank = "14"), ACE_CLUBS_BLUE, TWO_CLUBS, THREE_CLUBS))(prologConverter.optionalValueAce(Seq(QUEEN_CLUBS, KING_CLUBS, ACE_CLUBS_BLUE, ACE_CLUBS_BLUE, TWO_CLUBS, THREE_CLUBS)))

    assertResult(Seq(ACE_CLUBS_BLUE, TWO_CLUBS, THREE_CLUBS))(prologConverter.optionalValueAce(Seq(ACE_CLUBS_BLUE, TWO_CLUBS, THREE_CLUBS)))
  }

  test("Convert a tuple sequence into string") {

    val tupleSeq: Seq[(Int, String, String)] = Seq(QUEEN_CLUBS, KING_CLUBS) map (card => (card.rank.value, card.suit.shortName, card.color.shortName))
    val varX = new Var("X")

    assertResult("([" + tupleSeq.mkString(",") + "]).")(prologConverter.prologList(tupleSeq)(None))
    assertResult("([" + tupleSeq.mkString(",") + "]," + varX.getName + ").")(prologConverter.prologList(tupleSeq)(Some(varX)))
  }
}
