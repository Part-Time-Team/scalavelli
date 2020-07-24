package it.parttimeteam

/**
 * Represent the name of a generic player
 */
sealed trait Player{

  /**
   * Name of the player
   */
  val name:String
}

/**
 * Player class
 * @param name the name of the player
 */
case class ScalavelliPlayer(override val name:String) extends Player
