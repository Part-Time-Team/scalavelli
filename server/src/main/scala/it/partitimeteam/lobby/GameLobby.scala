package it.partitimeteam.lobby

import akka.japi.Pair
import it.parttimeteam.entities.Player

/**
 *
 * @param numberOfPlayers min number of players required to start a match
 */
case class GameLobby[T <: Player](numberOfPlayers: Int, override val players: List[T] = List())
  extends Lobby[T] {


  /**
   * @inheritdoc
   */
  override def hasEnoughPlayers: Boolean = players.length >= numberOfPlayers

  /**
   * @inheritdoc
   */
  override def extractPlayersForMatch(): Pair[Lobby[T], Option[List[T]]] =
    if (this.hasEnoughPlayers) {
      Pair(GameLobby(numberOfPlayers, players.drop(numberOfPlayers)), Some(players.take(numberOfPlayers)))
    } else {
      Pair(this, None)
    }

  /**
   * @inheritdoc
   */
  override def addPlayer(player: T): Lobby[T] =
    GameLobby(numberOfPlayers, players appended player)


  /**
   * @inheritdoc
   */
  override def removePlayer(playerId: String): Lobby[T] =
    GameLobby(numberOfPlayers, players.filter(p => p.id != playerId))


}
