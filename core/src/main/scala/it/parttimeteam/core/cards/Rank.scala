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
   * The first rank.
   */
  case class Ace() extends Rank(1, "Ace", "A") {
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
  case class Ten() extends Rank(10, "Ten", "0")

  /**
   * The eleventh rank.
   */
  case class Jack() extends Rank(11, "Jack", "J")

  /**
   * The twelveth rank.
   */
  case class Queen() extends Rank(12, "Queen", "Q")

  /**
   * The thirteenth rank.
   */
  case class King() extends Rank(13, "King", "K") {
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
    case "A" => Ace()
    case "2" => Two()
    case "3" => Three()
    case "4" => Four()
    case "5" => Five()
    case "6" => Six()
    case "7" => Seven()
    case "8" => Eight()
    case "9" => Nine()
    case "0" => Ten()
    case "J" => Jack()
    case "Q" => Queen()
    case "K" => King()
    case _ => throw new RuntimeException(f"Unknown rank $s")
  }

  implicit def value2rank(v: Int): Rank = v match {
    case 1 => Ace()
    case 2 => Two()
    case 3 => Three()
    case 4 => Four()
    case 5 => Five()
    case 6 => Six()
    case 7 => Seven()
    case 8 => Eight()
    case 9 => Nine()
    case 10 => Ten()
    case 11 => Jack()
    case 12 => Queen()
    case 13 => King()
    case _ => throw new RuntimeException(f"Unknown rank $v")
  }
}