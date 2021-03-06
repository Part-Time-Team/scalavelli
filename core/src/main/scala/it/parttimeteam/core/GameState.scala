package it.parttimeteam.core

import it.parttimeteam.core.collections.{Board, Deck}
import it.parttimeteam.core.player.Player
import it.parttimeteam.core.player.Player.PlayerId

/**
 * Current game state.
 *
 * @param deck    deck game.
 * @param board   board.
 * @param players list of player.
 */
case class GameState(deck: Deck, board: Board, players: Seq[Player]) {

  def getPlayer(id: PlayerId): Option[Player] = players.find(p => p.id equals id)

  def updatePlayer(player: Player): GameState =
    this.copy(players = players.map(p => if (p.id equals player.id) player else p))

  def playerWon(id: PlayerId): Boolean = {
    this.players.find(_.id == id).exists(_.hand.playerCards.isEmpty)
  }
}
