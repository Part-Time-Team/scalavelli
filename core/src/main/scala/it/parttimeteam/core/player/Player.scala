package it.parttimeteam.core.player

import it.parttimeteam.{Card, Hand}

/**
 * Represent of a generic player.
 *
 * @param name Name of the player.
 * @param id   Id of the player.
 * @param hand Hand of the player.
 */
sealed class Player(
                     val name: String,
                     val id: String,
                     val hand: Hand) {
}

// TODO: Add player without cards
  // TODO: This player must only have the number of cards in hands.
// TODO: Add player with cards
  // TODO: This player must have the list of cards in hands.

object Player {

  case class EmptyPlayer() extends Player("Empty", "0", Hand())

  case class NoHandPlayer(override val name: String,
                          override val id: String) extends Player(name, id, Hand())

  case class HandPlayer(override val name: String,
                        override val id: String,
                        override val hand: Hand) extends Player(name, id, hand)
}

/**
 * Player class
 *
 * @param name name of the player
 * @param id   id of the player
 * @param hand hand of the player
 */
case class ScalavelliPlayer(override val name: String,
                            override val id: String,
                            override val hand: Hand) extends Player(name, id, hand) {

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
    val hand: Hand = Hand(playerCards, tableCards)
    hand
  }
}
