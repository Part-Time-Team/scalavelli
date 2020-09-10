package it.parttimeteam.core.prolog.converter

import alice.tuprolog.{Prolog, Term, Var}
import it.parttimeteam.core.cards.Card
import it.parttimeteam.core.TestCards._
import it.parttimeteam.core.prolog.engine.PrologGameEngine
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should
import org.scalatest.prop.{TableDrivenPropertyChecks, TableFor1}
import org.scalatest.propspec.AnyPropSpec

class PrologUtilsSuite extends AnyFunSuite  with TableDrivenPropertyChecks with should.Matchers {

  test("Replace specific characters in a terms") {

    val prolog = new Prolog()
    val term: Term = prolog toTerm "['1']"
    val seq: Term = prolog toTerm "([(1,\"Clubs\"),(1,\"Spades\")])"

    assertResult("[1]")(PrologUtils.replaceTermToString(term, "'"))
    assertResult("[(1,'Clubs'),(1,'Spades')]")(PrologUtils.replaceTermToString(seq, "','"))
  }

  test("Split rank, suit and color from an array of strings"){

    val rankSuitColorToSplit: Array[String] = Array("10","(DR)")
    val result: (String, String, String) = ("10", "D", "R")

    assertResult(result)(PrologUtils.splitRankSuitColor(rankSuitColorToSplit))
  }

  class PrologUtilsPropSpec extends AnyPropSpec {

    val prologEngine: PrologGameEngine = new PrologGameEngine
    val prologConversion: PrologGameConverter = new PrologGameConverter

    /**
     * Card
     */
    val card: TableFor1[Seq[Card]] = Table(
      "card",
      Seq(FOUR_SPADES, FIVE_HEARTS, ACE_CLUBS_BLUE),
      Seq(KING_CLUBS, THREE_CLUBS, JACK_HEARTS)
    )

    property("Convert sequence cards into regex sequence") {
      forAll(card) { seq =>
        val termSeq: Seq[Term] = prologEngine goal "quicksortValue" + prologConversion.cardsConvertToString(seq)(Some(new Var("X")))
        PrologUtils.utils(termSeq).isEmpty should be(false)
      }
    }
  }

}