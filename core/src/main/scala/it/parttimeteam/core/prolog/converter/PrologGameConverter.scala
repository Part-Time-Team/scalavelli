package it.parttimeteam.core.prolog.converter

import alice.tuprolog.{Prolog, Term}
import it.parttimeteam.core.cards.Card

/**
 * Class to improve conversions results in prolog
 */
class PrologGameConverter extends PrologConverter {

  val prolog: Prolog = new Prolog()

  /**
   * Convert tuple-list in string
   */
  val convertList: List[(Int, String)] => String = list => {
    "([" + list.mkString(",") + "])."
  }

  override def toBoolean(list: List[Term]): Boolean = list.nonEmpty

  override def toStringAndReplace(term: Term): String = term.toString.replace("'", "")

  override def cardsConvert(cards: List[Card]): String = {
    convertList(for (card <- cards) yield (card.rank.value, card.suit.name))
  }
}
