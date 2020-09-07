package it.parttimeteam.core.cards

/**
 * One of the 13 rank of cards in the deck.
 *
 * @param value     Value.
 * @param name      Name.
 * @param shortName Symbol.
 */
sealed class Rank(val value: Int,
                  val name: String,
                  val shortName: String)
    extends Comparable[Rank] with Serializable {

  /**
   * Base compare between two ranks.
   *
   * @param t Rank to compare.
   * @return Compare result.
   */
  override def compareTo(t: Rank): Int = value compareTo t.value

  /**
   * Check if value of rank is the same of another rank.
   * @param obj Object to compare.
   * @return True if have the same value. false anywhere.
   */
  override def equals(obj: Any): Boolean = obj match {
    case obj: Rank => this.value equals obj.value
    case _ => super.equals(obj)
  }
}

object Rank {

  /**
   * Number rank
   */
  val ACE = "1"
  val TWO = "2"
  val THREE = "3"
  val FOUR = "4"
  val FIVE = "5"
  val SIX = "6"
  val SEVEN = "7"
  val EIGHT = "8"
  val NINE = "9"
  val TEN = "10"
  val JACK = "11"
  val QUEEN = "12"
  val KING = "13"
  val OVERFLOW_ACE = "14"

  /**
   * The first rank.
   */
  case class Ace() extends Rank(1, "Ace", "1") {
    /**
     * Compare an Ace with another card.
     *
     * @param t Rank to compare.
     * @return Compare result.
     */
    override def compareTo(t: Rank): Int = t match {
      case t if t.value equals 13 => 1
      case _ => super.compareTo(t)
    }
  }

  /**
   * The optional OverflowAce with value 14
   */
  case class OverflowAce() extends Rank(14, "Ace", "14")

  /**
   * The second rank.
   */
  case class Two() extends Rank(2, "Two", "2")

  /**
   * The third rank.
   */
  case class Three() extends Rank(3, "Three", "3")

  /**
   * The fourth rank.
   */
  case class Four() extends Rank(4, "Four", "4")

  /**
   * The fifth rank.
   */
  case class Five() extends Rank(5, "Five", "5")

  /**
   * The sixth rank.
   */
  case class Six() extends Rank(6, "Six", "6")

  /**
   * The seventh rank.
   */
  case class Seven() extends Rank(7, "Seven", "7")

  /**
   * The eigth rank.
   */
  case class Eight() extends Rank(8, "Eight", "8")

  /**
   * The ninth rank.
   */
  case class Nine() extends Rank(9, "Nine", "9")

  /**
   * The tenth rank.
   */
  case class Ten() extends Rank(10, "Ten", "10")

  /**
   * The eleventh rank.
   */
  case class Jack() extends Rank(11, "Jack", "11")

  /**
   * The twelveth rank.
   */
  case class Queen() extends Rank(12, "Queen", "12")

  /**
   * The thirteenth rank.
   */
  case class King() extends Rank(13, "King", "13") {
    /**
     * Compare King with another card.
     *
     * @param t Rank to compare.
     * @return Compare result.
     */
    override def compareTo(t: Rank): Int = t match {
      case t if t.value equals 1 => -1
      case _ => super.compareTo(t)
    }
  }

  /**
   * Transform a string in the matching rank.
   *
   * @param s String to convert.
   * @return Rank converted.
   */
  implicit def string2rank(s: String): Rank = s match {
    case this.ACE    => Ace()
    case this.TWO    => Two()
    case this.THREE  => Three()
    case this.FOUR   => Four()
    case this.FIVE   => Five()
    case this.SIX    => Six()
    case this.SEVEN  => Seven()
    case this.EIGHT  => Eight()
    case this.NINE   => Nine()
    case this.TEN    => Ten()
    case this.JACK   => Jack()
    case this.QUEEN  => Queen()
    case this.KING   => King()
    case this.OVERFLOW_ACE => OverflowAce()
    case _ => throw new RuntimeException(f"Unknown rank $s")
  }
}