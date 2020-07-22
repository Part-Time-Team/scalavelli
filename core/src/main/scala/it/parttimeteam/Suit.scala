package it.parttimeteam

/**
 * One of the 4 suit of the deck.
 *
 * @param name      Name.
 * @param shortName Symbol.
 */
abstract class Suit(val name: String, val shortName: String) extends Serializable

object Suit {

  /**
   * The Club suit.
   */
  case class Clubs() extends Suit("Clubs", "♣")

  /**
   * The spade suit.
   */
  case class Spades() extends Suit("Spades", "♠")

  /**
   * The diamond suit.
   */
  case class Diamonds() extends Suit("Diamonds", "♦")

  /**
   * The heart suit.
   */
  case class Hearts() extends Suit("Hearts", "♥")

  /**
   * Get all suits of the deck.
   *
   * @return A list with all suits.
   */
  def all: List[Suit] = List(Clubs(), Spades(), Diamonds(), Hearts())

  /**
   * Transform a string in the matching suit.
   *
   * @param s String to convert.
   * @return Suit converted.
   */
  implicit def string2suit(s: String): Suit = s match {
    case "♣" => Clubs()
    case "♠" => Spades()
    case "♦" => Diamonds()
    case "♥" => Hearts()
    case _ => throw new RuntimeException(f"Unknown suit $s")
  }
}