package it.parttimeteam.core.prolog.converter

import alice.tuprolog.Term
import it.parttimeteam.core.cards.Card

/**
 * Class to improve conversions results in prolog
 */
class PrologGameConverter extends PrologConverter {

  override def toBoolean(list: Seq[Term]): Boolean = list.nonEmpty

  override def toStringAndReplace(term: Term): String = term.toString.replace("'", "")

  override def cardsConvert(cards: Seq[Card]): String = {
    val tupleCard = for (card <- cards) yield (card.rank.value, card.suit.name)
    "([" + tupleCard.mkString(",") + "])."
  }

 /* def valueCards(cards: List[(Int, String)]): List[(Int, String)] = {

    def _valueCards(cards: List[(Int, String)]): List[(Int, String)] = {

      for (List(_, second) <- cards.grouped(2)) {
        case ((n1: Int, _), (n2: Int, _)) => if (n1 == 10 && n2 == 1) {}

      }
      _valueCards(cards)
    }
  }*/
}
