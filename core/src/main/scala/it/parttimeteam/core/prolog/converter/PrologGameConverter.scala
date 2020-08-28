package it.parttimeteam.core.prolog.converter

import alice.tuprolog.{Prolog, Term, Var}
import it.parttimeteam.core.cards.Rank.{Ace, King}
import it.parttimeteam.core.cards.Card
import it.parttimeteam.core.cards.Suit.{Clubs, Diamonds, Hearts, Spades}


/**
 * Class to improve conversions results in prolog
 */
class PrologGameConverter extends PrologConverter {

  val prolog = new Prolog()

  private val startList: String = "(["
  private val endList: String = "])."

  override def resultToBoolean(list: Seq[Term]): Boolean = list.nonEmpty

  override def resultToStringAndReplace(term: Term, replace: String): String = term.toString.replace(replace, "")

  override def cardsConvertToString(cards: Seq[Card])(variable: Option[Var]): String = {
    val tupleCard = for (card <- optionalValueCards(cards)) yield (card.rank.value, card.suit.name)
    listInProlog(tupleCard)(variable)
  }


  def sortedCard(cards: Seq[Term]): Seq[Card] = ???

  def collectSuit(cards: Seq[Card]): Seq[Seq[Card]] = cards.collectHearts +: cards.collectDiamonds +: cards.collectClubs +: cards.collectSpades +: Nil

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
            acc ++ (card.copy(rank = 14) +: Nil)
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

  implicit class collect(cards: Seq[Card]) {

    def collectClubs: Seq[Card] =
      cards.foldLeft(Seq.empty[Card]) {
        (acc, card) =>
          card.suit match {
            case Clubs() => acc ++ (card +: Nil)
            case _ => acc
          }
      }

    def collectSpades: Seq[Card] =
      cards.foldLeft(Seq.empty[Card]) {
        (acc, card) =>
          card.suit match {
            case Spades() => acc ++ (card +: Nil)
            case _ => acc
          }
      }

    def collectDiamonds: Seq[Card] =
      cards.foldLeft(Seq.empty[Card]) {
        (acc, card) =>
          card.suit match {
            case Diamonds() => acc ++ (card +: Nil)
            case _ => acc
          }
      }

    def collectHearts: Seq[Card] =
      cards.foldLeft(Seq.empty[Card]) {
        (acc, card) =>
          card.suit match {
            case Hearts() => acc ++ (card +: Nil)
            case _ => acc
          }
      }
  }
}

