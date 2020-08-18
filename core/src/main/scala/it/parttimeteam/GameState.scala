package it.parttimeteam

import it.parttimeteam.core.collections.Deck
import it.parttimeteam.core.player.Player

/**
 * Current game state.
 *
 * @param deck    deck game.
 * @param board   board.
 * @param players list of player.
 */
case class GameState(var deck: Deck, var board: Board, var players: Seq[Player]) {
  // TODO: Implement utilities functions.
  def >(id: String): Player =
    players.find(p => p.id equals id) match {
      case Some(p) => p
      case None => Player.EmptyPlayer()
    }

  def <(player: Player): GameState = {
    val list: List[Player] = players.filter(p => !(p.id equals player.id)).toList
    GameState(deck, board, player :: list)
  }
}
