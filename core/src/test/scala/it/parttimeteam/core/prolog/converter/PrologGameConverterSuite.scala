package it.parttimeteam.core.prolog.converter

import alice.tuprolog.{Prolog, Term, Var}
import it.parttimeteam.core.cards.{Card, Color, Rank, Suit}
import it.parttimeteam.core.prolog.TestCards
import it.parttimeteam.core.prolog.engine.PrologGameEngine
import org.scalatest.funsuite.AnyFunSuite

class PrologGameConverterSuite extends AnyFunSuite {

  val prologConverter: PrologGameConverter = new PrologGameConverter
  val prolog: Prolog = new Prolog

  test("Convert sequence of cards in string") {

    val sequence: Seq[Card] = Seq(TestCards.ACE_CLUBS, TestCards.FOUR_SPADES, TestCards.KING_DIAMONDS)

    assertResult("([(1,\"Clubs\"),(4,\"Spades\"),(13,\"Diamonds\")]).")(prologConverter.cardsConvertToString(sequence)(None))
    assertResult("([(1,\"Clubs\"),(4,\"Spades\"),(13,\"Diamonds\")],X).")(prologConverter.cardsConvertToString(sequence)(Some(new Var("X"))))
  }

  test("Convert a sequence of term into a tuple sequence") {

    val prologEngine = new PrologGameEngine
    val sequence: Seq[Card] = Seq(TestCards.ACE_CLUBS, TestCards.ACE_SPADES, TestCards.ACE_DIAMONDS)

    val termSeq: Seq[Term] = prologEngine goal "quicksortValue" + prologConverter.cardsConvertToString(sequence)(Some(new Var("X")))
    val tupleSeq: Seq[(Rank, Suit)] = sequence map (card => (card.rank, card.suit))

    assertResult(tupleSeq)(prologConverter.sortedCard(termSeq))
  }

  test("Get a card given the color, rank and suit") {
    val card: Card = Card(Rank.Ace(), Suit.Clubs(), Color.Blue())

    assertResult(card)(prologConverter getCard(prolog toTerm "B", prolog toTerm "Clubs", prolog toTerm "1"))
  }

  test("Change optional value of the card Ace if it is after King card") {

    assertResult(Seq(TestCards.QUEEN_CLUBS, TestCards.KING_CLUBS, TestCards.ACE_CLUBS.copy(rank = "14")))(prologConverter.optionalValueCards(Seq(TestCards.QUEEN_CLUBS, TestCards.KING_CLUBS, TestCards.ACE_CLUBS)))

    assertResult(Seq(TestCards.QUEEN_CLUBS, TestCards.KING_CLUBS, TestCards.ACE_CLUBS.copy(rank = "14"), TestCards.TWO_CLUBS, TestCards.THREE_CLUBS))(prologConverter.optionalValueCards(Seq(TestCards.QUEEN_CLUBS, TestCards.KING_CLUBS, TestCards.ACE_CLUBS, TestCards.TWO_CLUBS, TestCards.THREE_CLUBS)))

    assertResult(Seq(TestCards.ACE_CLUBS, TestCards.TWO_CLUBS, TestCards.THREE_CLUBS))(prologConverter.optionalValueCards(Seq(TestCards.ACE_CLUBS, TestCards.TWO_CLUBS, TestCards.THREE_CLUBS)))
  }

  test("Convert a tuple sequence into string") {

    val tupleSeq: Seq[(Int, String)] = Seq(TestCards.QUEEN_CLUBS, TestCards.KING_CLUBS) map (card => (card.rank.value, card.suit.name))
    val varX = new Var("X")

    assertResult("([" + tupleSeq.mkString(",") + "]).")(prologConverter.prologList(tupleSeq)(None))
    assertResult("([" + tupleSeq.mkString(",") + "]," + varX.getName + ").")(prologConverter.prologList(tupleSeq)(Some(varX)))
  }
}
