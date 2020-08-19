package it.parttimeteam.gamestate

import it.parttimeteam.Board
import it.parttimeteam.core.collections.Hand

/**
 * State used by the client side of the game,
 * contains the section of the game relevant to a specific user
 */
case class PlayerGameState(board: Board, hand: Hand, otherPlayers: Seq[Opponent])

/**
 * Opponent player with few fields.
 *
 * @param name         Name.
 * @param cardsNumbers Number of cards in hands.
 */
case class Opponent(name: String, cardsNumbers: Int)
