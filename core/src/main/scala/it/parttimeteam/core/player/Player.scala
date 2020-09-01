package it.parttimeteam.core.player

import it.parttimeteam.core.collections.Hand
import it.parttimeteam.core.player.Player.PlayerId

/**
 * Represent a generic player.
 *
 * @param name Name of the player.
 * @param id   Id of the player.
 * @param hand Hand of the player.
 */
case class Player(name: String, id: PlayerId, var hand: Hand)

object Player {
  type PlayerId = String
  type PlayerName = String
}