package it.parttimeteam.client

import it.parttimeteam.{Card, CardCombination, Player}

object ClientInterface {
  def apply(): ClientInterface[Player] = new ClientInterfaceImpl[Player](Player.empty)
}

trait ClientInterface[T <: Player] {

  /**
   * Get the current player.
   *
   * @return Current player.
   */
  def getPlayer: T

  /**
   * Add another players as enemies.
   * Those are controlled by other clients.
   *
   * @param players Another players.
   * @return List of all current enemies.
   */
  def addEnemies(players: T*): List[T]

  /**
   * Add cards to the current player hand.
   *
   * @param card One or more cards.
   */
  def addCards(card: Card*): Unit

  /**
   * Play a combination from current player hand.
   *
   * @param combination Combination to play.
   * @return True if success, false anywhere.
   */
  def playCombination(combination: CardCombination): Boolean
}

class ClientInterfaceImpl[T <: Player](var current: T) extends ClientInterface[T] {

  /**
   * Get all another players.
   *
   * @return List of players.
   */
  var enemies: List[T] = List.empty

  /**
   * @inheritdoc
   */
  override def getPlayer: T = current

  /**
   * @inheritdoc
   */
  override def addEnemies(players: T*): List[T] = {
    enemies = enemies ++ players
    enemies
  }

  // TODO: How can we add cards to a player hand?
  override def addCards(card: Card*): Unit = ???

  // TODO: How can we play combination for a player?
  override def playCombination(combination: CardCombination): Boolean = ???
}