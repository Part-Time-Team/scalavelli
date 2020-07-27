package it.parttimeteam

/**
 * Represent the name of a generic player
 */
sealed trait Player{

  /**
   * Name of the player
   */
  val name:String

  /**
   * Id of the player
   */
  val id:String

  /**
   * Hand of the player
   */
  val hand: Int // Hand
}

/**
 * Player class
 * @param name the name of the player
 * @param id the id of the player
 * @param hand the hand of the player
 */
case class ScalavelliPlayer(override val name:String,
                            override val id: String,
                            override val hand: Int) extends Player
