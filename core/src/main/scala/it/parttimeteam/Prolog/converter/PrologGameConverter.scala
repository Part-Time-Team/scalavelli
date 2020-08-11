package it.parttimeteam.Prolog.converter

import alice.tuprolog.{Prolog, Term}
import it.parttimeteam.Card

/**
 * Class to improve conversions results in prolog
 */
class PrologGameConverter extends PrologConverter {

  val prolog: Prolog = new Prolog()

  override def toInt(term: Term): Int = term.toString.toInt

  override def toTerm(stringTerm: String): Term = prolog toTerm stringTerm

  override def toStringAndReplace(term: Term): String = term.toString.replace("'", "")

  override def toTupleList(cards: List[Card]): List[(Int, String)] = ???
}
