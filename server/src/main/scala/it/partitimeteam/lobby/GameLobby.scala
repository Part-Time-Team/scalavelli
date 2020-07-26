package it.partitimeteam.lobby

import akka.japi.Pair
import it.partitimeteam.common.Referable

/**
 *
 * @param numberOfPlayers min number of players required to start a match
 */
case class GameLobby[T <: Referable](numberOfPlayers: Int, override val players: List[Pair[String, T]] = List())
  extends Lobby[T] {


  /**
   * @inheritdoc
   */
  override def hasEnoughPlayers: Boolean = players.length >= numberOfPlayers

  /**
   * @inheritdoc
   */
  override def extractPlayersForMatch(): Pair[Lobby[T], Option[List[Pair[String, T]]]] =
    if (this.hasEnoughPlayers) {
      Pair(GameLobby(numberOfPlayers, players.drop(numberOfPlayers)), Some(players.take(numberOfPlayers)))
    } else {
      Pair(this, None)
    }

  /**
   * @inheritdoc
   */
  override def addPlayer(username: String, data: T): Lobby[T] =
    GameLobby(numberOfPlayers, players appended Pair(username, data))


  /**
   * @inheritdoc
   */
  override def removePlayer(username: String): Lobby[T] =
    GameLobby(numberOfPlayers, players.filter(p => p.first != username))


}
