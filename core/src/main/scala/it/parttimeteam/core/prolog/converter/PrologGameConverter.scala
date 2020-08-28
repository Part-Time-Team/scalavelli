package it.parttimeteam.core.prolog.converter

import alice.tuprolog.{Prolog, Term, Var}
import it.parttimeteam.core.cards.Rank.{Ace, King}
import it.parttimeteam.core.cards.{Card, Rank}

/**
 * Class to improve conversions results in prolog
 */
class PrologGameConverter extends PrologConverter {

  val prolog = new Prolog()

  override def toBoolean(list: Seq[Term]): Boolean = list.nonEmpty

  override def toStringAndReplace(term: Term): String = term.toString.replace("'", "")

  // TODO da ovveridare
  def toStringAndReplace(terms: Seq[Term]): String = terms.map(term => term.toString.replace("'", "")).toString()

  override def cardsConvert(cards: Seq[Card])(variable : Option[Var]): String = {
    val tupleCard = for (card <- optionalValueCards(cards)) yield (card.rank.value, card.suit.name)

    //TODO rendere più leggibile
    if(variable.isDefined){
      "([" + tupleCard.mkString(",") +"]"+ "," + variable.get.getName+ ")."
    }
    else {
      "([" + tupleCard.mkString(",") + "])."
    }
  }

  /**
   * Converts the value card Ace if it is after King card
   *
   * @param cards sequence of cards
   * @return new sequence of cards
   */
  def optionalValueCards(cards: Seq[Card]): Seq[Card] = cards.foldLeft(Seq.empty[Card]) {
    (acc, card) =>
      acc match {
        case Nil => card +: Nil
        case _ => (acc.last.rank, card.rank) match {
          case (King(), Ace()) =>
            acc ++ (card.copy(rank = 14) +: Nil)
          case _ => acc ++ (card +: Nil)
        }
      }
  }
}
