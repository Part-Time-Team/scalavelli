package it.parttimeteam

/**
 * Represent of a generic player
 */
sealed trait Player{

  /**
   * Name of the player
   */
  val name: String

  /**
   * Id of the player
   */
  val id: String

  /**
   * Hand of the player
   */
  val hand: Hand
}

/**
 * Player class
 *
 * @param name name of the player
 * @param id id of the player
 * @param hand hand of the player
*/
case class ScalavelliPlayer(override val name:String,
                            override val id: String,
                            override val hand: Hand) extends Player {

  /**
   * Get name of the player
   *
   * @return Player name
   */
  def getName: String = f"Player name: $name"

  /**
   * Get id of the player
   *
   * @return Id player
   */
  def getId: String = f"Player id: $id"

  def refreshHand(playerCards: List[Card], tableCards: List[Card]): Hand = {
    val hand : Hand= Hand(playerCards, tableCards)
    hand
  }
}
