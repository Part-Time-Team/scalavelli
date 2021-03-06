package it.parttimeteam.core.prolog.converter

import alice.tuprolog.{Term, Var}
import it.parttimeteam.core.cards.Rank.{Ace, OverflowAce}
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
  def toString(term: Term, replace: String): String = PrologUtils.replaceTermToString(term, replace)

  /**
   * @inheritdoc
   */
  override def cardsConvertToString(cards: Seq[Card])(variable: Option[Var]): String = {
    val tupleCard = for (card <- cards) yield (card.rank.value, "\"" + card.suit.shortName + "\"", "\"" + card.color.shortName + "\"")
    this.prologList(tupleCard)(variable)
  }

  /**
   * @inheritdoc
   */
  override def sortedCard(cards: Seq[Term]): Seq[Card] = {

    val cardsList = PrologUtils.utils(cards)

    val tupleCard = cardsList map (card => {
      PrologUtils.splitRankSuitColor(card.toString().split(","))
    })
    tupleCard.map(item => Card(Rank.string2rank(item._1), Suit.string2suit(item._2), Color.string2color(item._3)))
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
  override def optionalValueAce(cards: Seq[Card]): Seq[Card] = {

    // List where overflowaces are converted into ace
    val convertList: Seq[Card] = cards.map(card => if (card.rank == OverflowAce()) card.copy(rank = Ace()) else card)

    val containAce: Seq[Card] = convertList.filter(card => card.rank == Rank.Ace())
    val containTwo: Boolean = convertList.exists(card => card.rank == Rank.Two())

    (containAce.size, containTwo) match {
      // Replacing Ace with OverflowAce with an ace into sequence
      case (1, false) =>
        convertList.foldLeft(Seq.empty[Card]) {
          (acc, card) =>
            card.rank match {
              case Ace() => acc ++ (card.copy(rank = "14") +: Nil)
              case _ => acc ++ (card +: Nil)
            }
        }
      // Replacing Ace with OverflowAce with more aces into sequence
      case (2, true) => convertList.foldLeft(Seq.empty[Card]) {
        (acc, card) =>
          card.rank match {
            case Ace() if !acc.exists(_.rank == Rank.OverflowAce()) => acc ++ (card.copy(rank = "14") +: Nil)
            case _ => acc ++ (card +: Nil)
          }
      }
      case _ => convertList
    }
  }


  /**
   * @inheritdoc
   */
  override def prologList(tupleCard: Seq[(Int, String, String)])(variable: Option[Var]): String = {

    if (variable.isDefined) {
      startList + tupleCard.mkString(",") + "]," + variable.get.getName + ")."
    } else {
      startList + tupleCard.mkString(",") + endList
    }
  }
}

