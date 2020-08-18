package it.parttimeteam.core.player

import it.parttimeteam.core.collections.Hand
import it.parttimeteam.core.player.Player.FullPlayer

/**
 * Represent a generic player.
 *
 * @param name Name of the player.
 * @param id   Id of the player.
 * @param hand Hand of the player.
 */
sealed class Player(name: String,
                    val id: String,
                    hand: Hand){

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
   *
   * @return GUID.
   */
  /* def getId: String = this match {
    case Player.EmptyPlayer() => "Empty player"
    case _ => f"Id: $id"
  }*/

  /**
   * Set the hand of the player.
   *
   * @param newHand New hand.
   * @return Player with a new hand.
   */
  def setHand(newHand: Hand): Player = this match {
    case _: FullPlayer => Player.FullPlayer(name, id, newHand)
    case _ => this
  }

  def getHand: Hand = hand

  /**
   * @inheritdoc
   */
  override def toString: String = this match {
    case Player.EmptyPlayer() => "Empty player"
    case _ => f"$getName $id"
  }
}

// TODO: Add player without cards
// TODO: This player must only have the number of cards in hands.
// TODO: Add player with cards
// TODO: This player must have the list of cards in hands.

object Player {

  case class EmptyPlayer() extends Player("", "", Hand())

  case class FullPlayer(name: String,
                        override val id: String,
                        hand: Hand) extends Player(name, id, hand)

}
