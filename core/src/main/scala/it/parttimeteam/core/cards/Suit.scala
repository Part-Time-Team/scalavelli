package it.parttimeteam.core.cards

/**
 * One of the 4 suit of the deck.
 *
 * @param name      Name.
 * @param shortName Symbol.
 */
sealed class Suit(val name: String, val shortName: String, val order: Int)
  extends Comparable[Suit] with Serializable {
  override def compareTo(t: Suit): Int = order compareTo t.order
}

object Suit {

  val HEARTS = "H"
  val DIAMONDS = "D"
  val SPADES = "S"
  val CLUBS = "C"

  /**
   * The heart suit.
   */
  case class Hearts() extends Suit("Hearts", "H", 0)

  /**
   * The diamond suit.
   */
  case class Diamonds() extends Suit("Diamonds", "D", 1)

  /**
   * The Club suit.
   */
  case class Clubs() extends Suit("Clubs", "C", 2)

  /**
   * The spade suit.
   */
  case class Spades() extends Suit("Spades", "S", 3)

  /**
   * Transform a string in the matching suit.
   *
   * @param s String to convert.
   * @return Suit converted.
   */
  implicit def string2suit(s: String): Suit = s match {
    case this.HEARTS => Hearts()
    case this.DIAMONDS => Diamonds()
    case this.CLUBS => Clubs()
    case this.SPADES => Spades()
    case _ => throw new RuntimeException(f"Unknown suit $s")
  }
}