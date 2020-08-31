package it.parttimeteam.core.prolog.converter

import alice.tuprolog.{Term, Var}
import it.parttimeteam.core.cards.Rank.{Ace, King}
import it.parttimeteam.core.cards.{Card, Color, Rank, Suit}

/**
 * Class to improve conversions results in prolog
 */
class PrologGameConverter extends PrologConverter {

  private val startList: String = "(["
  private val endList: String = "])."

  /**
   * @inheritdoc
   */
  override def toString(term: Term, replace: String): String = term.toString.replace(replace, "")

  /**
   * @inheritdoc
   */
  override def cardsConvertToString(cards: Seq[Card])(variable: Option[Var]): String = {
    val tupleCard = for (card <- optionalValueCards(cards)) yield (card.rank.value, "\"" + card.suit.name + "\"")
    this.prologList(tupleCard)(variable)
  }

  /**
   * @inheritdoc
   */
  override def sortedCard(cards: Seq[Term]): Seq[(Rank, Suit)] = {

    val cardsList = PrologUtils.utils(cards)

    val tupleCard = cardsList map (card => {
      val split = card.toString().split(",")
      (split(0), split(1))
    })
    tupleCard.map(item => (Rank.string2rank(item._1), Suit.string2suit(item._2)))
  }

  /**
   * @inheritdoc
   */
  override def getCard(color: Term, suit: Term, rank: Term): Card = {
    Card(Rank.string2rank(toString(rank, "'")), Suit.string2suit(toString(suit, "'")), Color.string2color(toString(color, "'")))
  }

  /**
   * @inheritdoc
   */
  override def optionalValueCards(cards: Seq[Card]): Seq[Card] = cards.foldLeft(Seq.empty[Card]) {
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
   * @inheritdoc
   */
  override def prologList(tupleCard: Seq[(Int, String)])(variable: Option[Var]): String =

    if (variable.isDefined) {
      startList + tupleCard.mkString(",") + "]," + variable.get.getName + ")."
    } else {
      startList + tupleCard.mkString(",") + endList
    }
}

