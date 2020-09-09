package it.parttimeteam.core.prolog

import it.parttimeteam.core.TestCards._
import it.parttimeteam.core.cards.Card
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.funspec.AnyFunSpec


class PrologGameSuite extends AnyFunSuite with AnyFunSpec {

  val prologGame: PrologGame = PrologGame()

  test("Load deck") {
    assert(prologGame.loadDeck.size equals 104)
  }

  test("Sort a sequence of cards by value") {

    val seq1: Seq[Card] = Seq(QUEEN_CLUBS, KING_SPADES, ACE_CLUBS_BLUE, TWO_HEARTS)
    val resultSeq1: Seq[Card] = Seq(ACE_CLUBS_BLUE, TWO_HEARTS, QUEEN_CLUBS, KING_SPADES)

    assertResult(resultSeq1)(prologGame.sortByRank(seq1))

    val seq2: Seq[Card] = Seq(SIX_HEARTS, SEVEN_HEARTS, FIVE_HEARTS)
    val resultSeq2: Seq[Card] = Seq(FIVE_HEARTS, SIX_HEARTS, SEVEN_HEARTS)

    assertResult(resultSeq2)(prologGame.sortByRank(seq2))
  }

  test("Sort a sequence of cards by suit") {

    val seq1: Seq[Card] = Seq(QUEEN_CLUBS, KING_SPADES, ACE_CLUBS_BLUE, TWO_HEARTS)
    val resultSeq1: Seq[Card] = Seq(TWO_HEARTS, ACE_CLUBS_BLUE, QUEEN_CLUBS, KING_SPADES)

    assertResult(resultSeq1)(prologGame.sortBySuit(seq1))

    val seq2: Seq[Card] = Seq(SIX_HEARTS, KING_SPADES, ACE_CLUBS_BLUE, TWO_HEARTS)
    val resultSeq2: Seq[Card] = Seq(TWO_HEARTS, SIX_HEARTS, ACE_CLUBS_BLUE, KING_SPADES)

    assertResult(resultSeq2)(prologGame.sortBySuit(seq2))

  }

  describe("Test double ace in full scale") {
    it ("Should correctly sort a double ace") {
      val seq3: Seq[Card] = Seq(ACE_SPADES, ACE_SPADES, TWO_SPADES, THREE_SPADES, FOUR_SPADES, FIVE_SPADES, SIX_SPADES, SEVEN_SPADES, EIGHT_SPADES, NINE_SPADES, TEN_SPADES, JACK_SPADES, QUEEN_SPADES, KING_SPADES)
      val resultSeq3: Seq[Card] = Seq(ACE_SPADES, TWO_SPADES, THREE_SPADES, FOUR_SPADES, FIVE_SPADES, SIX_SPADES, SEVEN_SPADES, EIGHT_SPADES, NINE_SPADES, TEN_SPADES, JACK_SPADES, QUEEN_SPADES, KING_SPADES, OVERFLOW_ACE_SPADES)

      assertResult(resultSeq3)(prologGame.sortBySuit(seq3))
    }

    it ("Should correctly sort a ace and an overflow ace") {
      val seq4: Seq[Card] = Seq(ACE_SPADES, OVERFLOW_ACE_SPADES, TWO_SPADES, THREE_SPADES, FOUR_SPADES, FIVE_SPADES, SIX_SPADES, SEVEN_SPADES, EIGHT_SPADES, NINE_SPADES, TEN_SPADES, JACK_SPADES, QUEEN_SPADES, KING_SPADES)
      val resultSeq4: Seq[Card] = Seq(ACE_SPADES, TWO_SPADES, THREE_SPADES, FOUR_SPADES, FIVE_SPADES, SIX_SPADES, SEVEN_SPADES, EIGHT_SPADES, NINE_SPADES, TEN_SPADES, JACK_SPADES, QUEEN_SPADES, KING_SPADES, OVERFLOW_ACE_SPADES)

      assertResult(resultSeq4)(prologGame.sortBySuit(seq4))

    }

    it ("Should correctly sort a double overflow ace") {
      val seq5: Seq[Card] = Seq(OVERFLOW_ACE_SPADES, OVERFLOW_ACE_SPADES, TWO_SPADES, THREE_SPADES, FOUR_SPADES, FIVE_SPADES, SIX_SPADES, SEVEN_SPADES, EIGHT_SPADES, NINE_SPADES, TEN_SPADES, JACK_SPADES, QUEEN_SPADES, KING_SPADES)
      val resultSeq5: Seq[Card] = Seq(ACE_SPADES, TWO_SPADES, THREE_SPADES, FOUR_SPADES, FIVE_SPADES, SIX_SPADES, SEVEN_SPADES, EIGHT_SPADES, NINE_SPADES, TEN_SPADES, JACK_SPADES, QUEEN_SPADES, KING_SPADES, OVERFLOW_ACE_SPADES)

      assertResult(resultSeq5)(prologGame.sortBySuit(seq5))
    }
  }
}


