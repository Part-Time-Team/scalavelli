package it.parttimeteam.core.cards

/**
 * One of the two color of the deck.
 *
 * @param name color of card
 * @param shortName shortName of color
 */
sealed class Color(val name: String,
                   val shortName: String) extends Serializable

object Color {

  /**
   * Color blue of card
   */
  case class Blue() extends Color("Blue", "B")

  /**
   * Color red of card
   */
  case class Red() extends Color("Red", "R")

  /**
   * Transform a string in the matching color.
   *
   * @param c String to convert.
   * @return Color converted.
   */
  implicit def string2color(c: String) : Color = c match {
    case "B" => Blue()
    case "R" => Red()
    case _ => throw new RuntimeException(f"Unknown color $c")
  }
}

