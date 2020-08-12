package it.parttimeteam.core

import it.parttimeteam.core.client.ClientInterface
import it.parttimeteam.core.player.Player.{HandPlayer, NoHandPlayer}
import it.parttimeteam.core.server.ServerInterface
import it.parttimeteam.{Card, CardCombination}

trait Interface {

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

object ManagerBuilder {

  object Manager {

    sealed trait EmptyManager extends Interface

    sealed trait ManagerWithPlayer extends Interface {
      def player: HandPlayer
    }

    sealed trait ManagerWithPlayers extends Interface

    sealed trait ManagerWithEnemies extends Interface

    type Client <: {
      def player: HandPlayer
      def enemies: List[NoHandPlayer]
    }

    type Server <: {
      def players: List[NoHandPlayer]
    }
  }

  // TODO: Those are needed? https://gist.github.com/MaximilianoFelice/55ffa549172799fd359415a79a1f2d17
  def apply[T <: Interface](players: List[HandPlayer] = List(),
                            enemies: List[NoHandPlayer] = List()): ManagerBuilder[T] = new ManagerBuilder[T](players, enemies)

  def apply(): ManagerBuilder[Manager.EmptyManager] = apply[Manager.EmptyManager](List(), List())
}

class ManagerBuilder[Manager <: Interface] protected(
                                                      players: List[HandPlayer] = List(),
                                                      enemies: List[NoHandPlayer] = List()) {

  import ManagerBuilder.Manager._

  def addPlayer(player: HandPlayer): ManagerBuilder[Manager with ManagerWithPlayer] = {
    new ManagerBuilder(players :+ player, enemies)
  }

  def addEnemies(enemy: NoHandPlayer*): ManagerBuilder[Manager with ManagerWithEnemies] =
    new ManagerBuilder(players, enemies :++ enemy)

  def build(): Interface =
    if (players.size > 1 && enemies.isEmpty)
      ServerInterface(players)
    else
      ClientInterface(players.head, enemies)
}

/*
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
}*/