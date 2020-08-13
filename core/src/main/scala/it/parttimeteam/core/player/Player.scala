package it.parttimeteam.core.player

import it.parttimeteam.Hand

/**
 * Represent a generic player.
 *
 * @param name Name of the player.
 * @param id   Id of the player.
 * @param hand Hand of the player.
 */
sealed class Player(name: String,
                    id: String,
                    val hand: Hand) {

  /**
   * Get name of the player
   *
   * @return Player name
   */
  def getName: String = this match {
    case Player.EmptyPlayer() => "Empty player"
    case _ => f"Name: $name"
  }

  /**
   * Get Id of the player. This is a unique GUID identifier.
   * @return GUID.
   */
  def getId: String = this match {
    case Player.EmptyPlayer() => "Empty player"
    case _ => f"Id: $id"
  }

  /**
   * @inheritdoc
   */
  override def toString: String = this match {
    case Player.EmptyPlayer() => "Empty player"
    case _ => f"$getName $getId"
  }
}

// TODO: Add player without cards
// TODO: This player must only have the number of cards in hands.
// TODO: Add player with cards
// TODO: This player must have the list of cards in hands.

object Player {

  case class EmptyPlayer() extends Player("", "", Hand())

  case class FullPlayer(name: String,
                        id: String,
                        override val hand: Hand) extends Player(name, id, hand)

}
