package it.parttimeteam.core.prolog.converter

import alice.tuprolog.{Term, Var}
import it.parttimeteam.core.cards.Rank.{Ace, King}
import it.parttimeteam.core.cards.{Card, Rank, Suit}


/**
 * Class to improve conversions results in prolog
 */
class PrologGameConverter extends PrologConverter {

  private val startList: String = "(["
  private val endList: String = "])."

  override def resultToBoolean(list: Seq[Term]): Boolean = list.nonEmpty

  override def resultToStringAndReplace(term: Term, replace: String): String = term.toString.replace(replace, "")

  override def cardsConvertToString(cards: Seq[Card])(variable: Option[Var]): String = {
    val tupleCard = for (card <- optionalValueCards(cards)) yield (card.rank.value, "\"" + card.suit.name + "\"")
    listInProlog(tupleCard)(variable)
  }

  // TODO da testare
  def sortedCard(cards: Seq[Term]): List[(Rank, Suit)] = {

    val cardsList = PrologUtils.utils(cards)

    val tupleCard = cardsList map (card => {
      val split = card.toString().split(",")
      (split(0), split(1))
    })
    tupleCard.map(item => (Rank.string2rank(item._1), Suit.string2suit(item._2)))
  }

  /**
   * Converts the value card Ace if it is after King card
   *
   * @param cards sequence of cards
   * @return new sequence of cards
   */
  // TODO renderlo PRIVATE - cambiare i test
  def optionalValueCards(cards: Seq[Card]): Seq[Card] = cards.foldLeft(Seq.empty[Card]) {
    (acc, card) =>
      acc match {
        case Nil => card +: Nil
        case _ => (acc.last.rank, card.rank) match {
          case (King(), Ace()) =>
            acc ++ (card.copy(rank = "14") +: Nil)
          case _ => acc ++ (card +: Nil)
        }
      }
  }

  /**
   * Convert tuple sequence in a prolog list
   *
   * @param tupleCard tuple sequence to convert
   * @param variable  variable defined
   * @return prolog string list
   */
  private def listInProlog(tupleCard: Seq[(Int, String)])(variable: Option[Var]): String =

    if (variable.isDefined) {
      startList + tupleCard.mkString(",") + "]" + "," + variable.get.getName + ")."
    } else {
      startList + tupleCard.mkString(",") + endList
    }
}

