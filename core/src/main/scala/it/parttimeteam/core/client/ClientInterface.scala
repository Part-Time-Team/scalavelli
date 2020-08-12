/*
 * Build pattern learned from: https://medium.com/@maximilianofelice/builder-pattern-in-scala-with-phantom-types-3e29a167e863
 */
package it.parttimeteam.core.client

import it.parttimeteam.core.Interface
import it.parttimeteam.core.player.Player
import it.parttimeteam.{Card, CardCombination}

case class ClientInterface(player: Player, enemies: List[Player]) extends Interface {
  /**
   * Add cards to the current player hand.
   *
   * @param card One or more cards.
   */
  override def addCards(card: Card*): Unit = ???

  /**
   * Play a combination from current player hand.
   *
   * @param combination Combination to play.
   * @return True if success, false anywhere.
   */
  override def playCombination(combination: CardCombination): Boolean = ???
}