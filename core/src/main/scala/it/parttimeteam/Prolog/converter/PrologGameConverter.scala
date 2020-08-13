package it.parttimeteam.Prolog.converter

import alice.tuprolog.{Prolog, Term}
import it.parttimeteam.Card

/**
 * Class to improve conversions results in prolog
 */
class PrologGameConverter extends PrologConverter {

  val prolog: Prolog = new Prolog()

  /**
   * Check last element in a list
   */
  val lastElement: (Int, List[(Int, String)]) => String = (i, list) => if (i != list.size - 1) "," else ""

  /**
   * Convert tuple-list in string
   */
  val convertList: List[(Int, String)] => String = list => {
    var stringList = ""
    for (i <- list.indices) {
      stringList += list(i) + lastElement(i, list)
    }
    "([" + stringList + "])."
  }

  override def toInt(term: Term): Int = term.toString.toInt

  override def toTerm(stringTerm: String): Term = prolog toTerm stringTerm

  override def toBoolean(list: List[Term]): Boolean = list match {
    case l if l.nonEmpty => true
    case _ => false
  }

  override def toStringAndReplace(term: Term): String = term.toString.replace("'", "")

  override def cardsConvert(cards: List[Card]): String = {
    val tupleList: List[(Int, String)] = for (card <- cards) yield (card.rank.value, card.suit.name)
    convertList(tupleList)
  }
}
