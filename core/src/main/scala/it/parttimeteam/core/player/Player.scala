package it.parttimeteam.core.player

import it.parttimeteam.core.collections.Hand

object Player {
  type PlayerId = String

  /**
   * Represent a generic player.
   *
   * @param name Name of the player.
   * @param id   Id of the player.
   * @param hand Hand of the player.
   */
  case class Player(name: String, id: PlayerId, var hand: Hand)

  def apply(name: String, id: PlayerId, hand: Hand): Player = Player(name, id, hand)
}

