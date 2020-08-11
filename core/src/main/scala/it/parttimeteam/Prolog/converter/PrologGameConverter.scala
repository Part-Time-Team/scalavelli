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

  override def toTermList(list: String): Term = prolog toTerm "[" + list + "]"

  override def toStringAndReplace(term: Term): String = term.toString.replace("'", "")

  override def toTupleList(cards: List[Card]): String = {

    val tupleList : List[String] = for (card <- cards) yield (card.rank.value, card.suit.name).toString()
    tupleList head //TODO continuare da qui -- restituire tutto il contenuto della stringa
  }
}
