package it.parttimeteam.core.prolog.converter

import alice.tuprolog.{Prolog, Term, Var}
import it.parttimeteam.core.cards.{Card, Color, Rank, Suit}
import it.parttimeteam.core.prolog.engine.PrologGameEngine
import org.scalatest.funsuite.AnyFunSuite

import scala.util.matching.Regex

class PrologUtilsSpec extends AnyFunSuite{

  val prologEngine : PrologGameEngine = new PrologGameEngine
  val prologConversion : PrologGameConverter = new PrologGameConverter

  // TODO fallisce sempre 
  /*test("Convert sequence cards into regex sequence"){

    val card1: Card = Card(Rank.Four(), Suit.Clubs(), Color.Red())
    val termSeq : Seq[Term] = prologEngine goal "quicksortValue" + prologConversion.cardsConvertToString(Seq(card1))(Some(new Var("X")))

    val pattern: Regex = "[0-9]+,[a-zA-Z]+".r
    val regexSeq : Seq[Regex.Match] = (pattern findAllMatchIn "(4,Clubs)") toList

    assertResult(pattern.findAllMatchIn("4,Clubs").toList)(pattern.findAllMatchIn("4,Clubs").toList)
  }*/

  // TODO lasciare il test solo qui o solo nei test del converter o in tutti e due?
  test("Replace specific characters in a terms"){

    val prolog = new Prolog()
    val term: Term = prolog toTerm "['1']"
    val seq: Term = prolog toTerm "([(1,\"Clubs\"),(1,\"Spades\")])"

    assertResult("[1]")(PrologUtils.replaceTermToString(term, "'"))
    assertResult("[(1,'Clubs'),(1,'Spades')]")(PrologUtils.replaceTermToString(seq, "','"))
  }
}
