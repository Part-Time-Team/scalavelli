package it.parttimeteam

import it.parttimeteam.core.collections.Deck
import it.parttimeteam.core.player.Player

/**
 * Current game state.
 *
 * @param players list of player.
 * @param deck    deck game.
 * @param board   board.
 */
case class GameState(players: List[Player], deck: Deck, board: Board) {
  // TODO: Implement utilities functions.
}
