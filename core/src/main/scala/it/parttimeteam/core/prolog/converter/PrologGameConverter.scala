package it.parttimeteam.core.prolog.converter

import alice.tuprolog.{Term}
import it.parttimeteam.core.cards.Card

/**
 * Class to improve conversions results in prolog
 */
class PrologGameConverter extends PrologConverter {

  override def toBoolean(list: List[Term]): Boolean = list.nonEmpty

  override def toStringAndReplace(term: Term): String = term.toString.replace("'", "")

  override def cardsConvert(cards: List[Card]): String = {
    val tupleCard = for (card <- cards) yield (card.rank.value, card.suit.name)
    "([" + tupleCard.mkString(",") + "])."
  }

  def valueCards(cards: List[(Int, String)]): Unit = {

    def _valueCards(cards: List[(Int, String)]) {
      for (List(first, second) <- cards.grouped(2)) {}
    }
    _valueCards(cards)
  }
}
