package it.parttimeteam.core.server

import it.parttimeteam.core.Interface
import it.parttimeteam.{Card, CardCombination}
import it.parttimeteam.core.player.Player

case class ServerInterface(players: List[Player]) extends Interface {
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