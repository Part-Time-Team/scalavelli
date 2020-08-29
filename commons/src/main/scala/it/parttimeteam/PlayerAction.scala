package it.parttimeteam

import it.parttimeteam.core.collections.{Board, Hand}

sealed class PlayerAction

/**
 * Ends turn drawing a card from the deck
 */
case object DrawCard extends PlayerAction

case class PlayedMove(updatedHand: Hand, updatedBoard: Board) extends PlayerAction



