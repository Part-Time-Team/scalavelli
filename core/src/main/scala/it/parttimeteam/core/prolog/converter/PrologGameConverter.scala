package it.parttimeteam.core.prolog.converter

import alice.tuprolog.Term
import it.parttimeteam.core.cards.Rank.{Ace, King}
import it.parttimeteam.core.cards.{Card, Rank}


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

  def optionalValueCards(cards: Seq[Card]): Seq[Card] = cards.foldLeft(Seq.empty[Card]) {
    (acc, card) =>
      acc match {
        case Nil => card +: Nil
        case _ => (acc.last.rank, card.rank) match {
          case (King(), Ace()) => {
            acc ++ (card.copy(rank = 14) +: Nil)
          }
          case _ => acc ++ (card +: Nil)
        }
      }
  }
}
