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

    val sequence: Seq[Card] = Seq(ACE_CLUBS, FOUR_SPADES, KING_DIAMONDS)

    assertResult("([(1,\"C\"),(4,\"S\"),(13,\"D\")]).")(prologConverter.cardsConvertToString(sequence)(None))
    assertResult("([(1,\"C\"),(4,\"S\"),(13,\"D\")],X).")(prologConverter.cardsConvertToString(sequence)(Some(new Var("X"))))
  }

  test("Convert a sequence of term into a tuple sequence") {

    val prologEngine = new PrologGameEngine
    val sequence: Seq[Card] = Seq(ACE_CLUBS, ACE_SPADES, ACE_DIAMONDS)

    val termSeq: Seq[Term] = prologEngine goal "quicksortValue" + prologConverter.cardsConvertToString(sequence)(Some(new Var("X")))
    val tupleSeq: Seq[(Rank, Suit)] = sequence map (card => (card.rank, card.suit))

    assertResult(tupleSeq)(prologConverter.sortedCard(termSeq))
  }

  test("Get a card given the color, rank and suit") {
    val card: Card = Card(Rank.Ace(), Suit.Clubs(), Color.Blue())

    assertResult(card)(prologConverter getCard(prolog toTerm "B", prolog toTerm "C", prolog toTerm "1"))
  }

  test("Change optional value of the card Ace if it is after King card") {

    assertResult(Seq(QUEEN_CLUBS, KING_CLUBS, ACE_CLUBS.copy(rank = "14")))(prologConverter.optionalValueCards(Seq(QUEEN_CLUBS, KING_CLUBS, ACE_CLUBS)))

    assertResult(Seq(QUEEN_CLUBS, KING_CLUBS, ACE_CLUBS.copy(rank = "14"), TWO_CLUBS, THREE_CLUBS))(prologConverter.optionalValueCards(Seq(QUEEN_CLUBS, KING_CLUBS, ACE_CLUBS, TWO_CLUBS, THREE_CLUBS)))

    assertResult(Seq(ACE_CLUBS, TWO_CLUBS, THREE_CLUBS))(prologConverter.optionalValueCards(Seq(ACE_CLUBS, TWO_CLUBS, THREE_CLUBS)))
  }

  test("Convert a tuple sequence into string") {

    val tupleSeq: Seq[(Int, String)] = Seq(QUEEN_CLUBS, KING_CLUBS) map (card => (card.rank.value, card.suit.name))
    val varX = new Var("X")

    assertResult("([" + tupleSeq.mkString(",") + "]).")(prologConverter.prologList(tupleSeq)(None))
    assertResult("([" + tupleSeq.mkString(",") + "]," + varX.getName + ").")(prologConverter.prologList(tupleSeq)(Some(varX)))
  }
}
