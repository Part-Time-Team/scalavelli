package it.parttimeteam.core.prolog

import it.parttimeteam.core.cards.Card
import it.parttimeteam.core.prolog.TestCards._
import org.scalatest.funsuite.AnyFunSuite

class PrologGameSuite extends AnyFunSuite {

  val prologGame: PrologGame = PrologGame()

  test("Load deck") {
    assert(prologGame.loadDeck.size equals 104)
  }

  test("Sort a sequence of cards by value") {

    val seq1: Seq[Card] = Seq(QUEEN_CLUBS, KING_SPADES, ACE_CLUBS, TWO_HEARTS)
    val resultSeq1: Seq[Card] = Seq(ACE_CLUBS, TWO_HEARTS, QUEEN_CLUBS, KING_SPADES)

    assertResult(resultSeq1)(prologGame.sortByRank(seq1))

    val seq2: Seq[Card] = Seq(SIX_HEARTS, SEVEN_HEARTS, FIVE_HEARTS)
    val resultSeq2: Seq[Card] = Seq(FIVE_HEARTS, SIX_HEARTS, SEVEN_HEARTS)

    assertResult(resultSeq2)(prologGame.sortByRank(seq2))
  }

  test("Sort a sequence of cards by suit") {

    val seq1: Seq[Card] = Seq(QUEEN_CLUBS, KING_SPADES, ACE_CLUBS, TWO_HEARTS)
    val resultSeq1: Seq[Card] = Seq(TWO_HEARTS, ACE_CLUBS, QUEEN_CLUBS, KING_SPADES)

    assertResult(resultSeq1)(prologGame.sortBySuit(seq1))

    val seq2: Seq[Card] = Seq(SIX_HEARTS, KING_SPADES, ACE_CLUBS, TWO_HEARTS)
    val resultSeq2: Seq[Card] = Seq(TWO_HEARTS, SIX_HEARTS, ACE_CLUBS, KING_SPADES)

    assertResult(resultSeq2)(prologGame.sortBySuit(seq2))
  }
}


