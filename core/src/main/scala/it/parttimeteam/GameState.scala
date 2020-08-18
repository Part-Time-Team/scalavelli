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
case class GameState(players: List[Player], var deck: Deck, var board: Board) {
  // TODO: Implement utilities functions.
  def >(id: String): Player =
    players.find(p => p.id equals id)
      .getOrElse(Player.EmptyPlayer())

  def <(player: Player): GameState = {
    val list = players dropWhile (p => p.id equals player.id)
    GameState(list.::(player), deck, board)
  }
}
