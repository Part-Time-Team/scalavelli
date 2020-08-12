package it.parttimeteam

import it.parttimeteam.core.player.Player

/**
 * Represent the game entity
 *
 * @param players       list of player
 * @param deck          deck game
 * @param currentPlayer current player in the round
 * @param gameBoard     game board
 */
case class Game(players: List[Player], deck: Null, currentPlayer: Int, gameBoard: GameBoard) {

  /**
   * Gets the next player based on the turn
   *
   * @return next player
   */
  def getNextPlayer: Player = players((currentPlayer + 1) % players.size)

}
