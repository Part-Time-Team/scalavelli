package it.parttimeteam

abstract class Suit(val name: String, val shortName: String) extends Serializable

object Suit {

  case class Clubs() extends Suit("Clubs", "♣")

  case class Spades() extends Suit("Spades", "♠")

  case class Diamonds() extends Suit("Diamonds", "♦")

  case class Hearts() extends Suit("Hearts", "♥")

  def all: List[Suit] = List(Clubs(), Spades(), Diamonds(), Hearts())

  implicit def string2suit(s: String): Suit = s match {
    case "♣" => Clubs()
    case "♠" => Spades()
    case "♦" => Diamonds()
    case "♥" => Hearts()
    case _ => throw new RuntimeException(f"Unknown suit $s")
  }
}