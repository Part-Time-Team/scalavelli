package it.parttimeteam.core.prolog

import it.parttimeteam.core.cards.Card
import org.scalatest.funsuite.AnyFunSuite

class PrologGameSuite extends AnyFunSuite {

  val prologGame: PrologGame = PrologGame()

  test("Load deck") {
    assert(prologGame.loadDeck.size equals 104)
  }

  test("Sort a sequence of cards by value") {

    val seq1: Seq[Card] = Seq(Cards().QUEEN_CLUBS, Cards().KING_SPADES, Cards().ACE_CLUBS, Cards().TWO_HEARTS)
    val resultSeq1: Seq[Card] = Seq(Cards().ACE_CLUBS, Cards().TWO_HEARTS, Cards().QUEEN_CLUBS, Cards().KING_SPADES)

    assertResult(resultSeq1)(prologGame.sortByRank(seq1))

    val seq2: Seq[Card] = Seq(Cards().SIX_HEARTS, Cards().SEVEN_HEARTS, Cards().FIVE_HEARTS)
    val resultSeq2: Seq[Card] = Seq(Cards().FIVE_HEARTS, Cards().SIX_HEARTS, Cards().SEVEN_HEARTS)

    assertResult(resultSeq2)(prologGame.sortByRank(seq2))
  }

  test("Sort a sequence of cards by suit") {

    val seq1: Seq[Card] = Seq(Cards().QUEEN_CLUBS, Cards().KING_SPADES, Cards().ACE_CLUBS, Cards().TWO_HEARTS)
    val resultSeq1: Seq[Card] = Seq(Cards().TWO_HEARTS, Cards().ACE_CLUBS, Cards().QUEEN_CLUBS, Cards().KING_SPADES)

    assertResult(resultSeq1)(prologGame.sortBySuit(seq1))

    val seq2: Seq[Card] = Seq(Cards().SIX_HEARTS, Cards().KING_SPADES, Cards().ACE_CLUBS, Cards().TWO_HEARTS)
    val resultSeq2: Seq[Card] = Seq(Cards().TWO_HEARTS, Cards().SIX_HEARTS, Cards().ACE_CLUBS, Cards().KING_SPADES)

    assertResult(resultSeq2)(prologGame.sortBySuit(seq2))
  }
}


