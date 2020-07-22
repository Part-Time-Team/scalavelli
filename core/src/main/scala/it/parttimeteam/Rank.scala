package it.parttimeteam

/**
 * One of the 13 rank of cards in the deck.
 * @param value Value.
 * @param name Name.
 * @param shortName Symbol.
 */
abstract class Rank(val value: Int, val name: String, val shortName: String) extends Serializable

object Rank {

  /**
   * The first rank.
   */
  case class Ace() extends Rank(14, "Ace", "A")

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
  case class Jack() extends Rank(11, "Jack", "J")

  /**
   * The twelveth rank.
   */
  case class Queen() extends Rank(12, "Queen", "Q")

  /**
   * The thirteenth rank.
   */
  case class King() extends Rank(13, "King", "K")

  /**
   * Get all ranks of the deck.
   * @return A list with all suits.
   */
  def all: List[Rank] = List(Ace(), Two(), Three(), Four(), Five(), Six(), Seven(), Eight(), Nine(), Ten(), Jack(), Queen(), King())

  /**
   * Transform a string in the matching rank.
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
}