package it.parttimeteam.core.prolog

import it.parttimeteam.core.cards.Card
import org.scalatest.funsuite.AnyFunSuite

class PrologGameSuite extends AnyFunSuite {

  val prologGame: PrologGame = PrologGame()

  test("Load deck") {
    assert(prologGame.loadDeck.size equals 104)
  }

  test("Sort a sequence of cards by value") {

    val seq1: Seq[Card] = Seq(TestCards.QUEEN_CLUBS, TestCards.KING_SPADES, TestCards.ACE_CLUBS, TestCards.TWO_HEARTS)
    val resultSeq1: Seq[Card] = Seq(TestCards.ACE_CLUBS, TestCards.TWO_HEARTS, TestCards.QUEEN_CLUBS, TestCards.KING_SPADES)

    assertResult(resultSeq1)(prologGame.sortByRank(seq1))

    val seq2: Seq[Card] = Seq(TestCards.SIX_HEARTS, TestCards.SEVEN_HEARTS, TestCards.FIVE_HEARTS)
    val resultSeq2: Seq[Card] = Seq(TestCards.FIVE_HEARTS, TestCards.SIX_HEARTS, TestCards.SEVEN_HEARTS)

    assertResult(resultSeq2)(prologGame.sortByRank(seq2))
  }

  test("Sort a sequence of cards by suit") {

    val seq1: Seq[Card] = Seq(TestCards.QUEEN_CLUBS, TestCards.KING_SPADES, TestCards.ACE_CLUBS, TestCards.TWO_HEARTS)
    val resultSeq1: Seq[Card] = Seq(TestCards.TWO_HEARTS, TestCards.ACE_CLUBS, TestCards.QUEEN_CLUBS, TestCards.KING_SPADES)

    assertResult(resultSeq1)(prologGame.sortBySuit(seq1))

    val seq2: Seq[Card] = Seq(TestCards.SIX_HEARTS, TestCards.KING_SPADES, TestCards.ACE_CLUBS, TestCards.TWO_HEARTS)
    val resultSeq2: Seq[Card] = Seq(TestCards.TWO_HEARTS, TestCards.SIX_HEARTS, TestCards.ACE_CLUBS, TestCards.KING_SPADES)

    assertResult(resultSeq2)(prologGame.sortBySuit(seq2))
  }
}


