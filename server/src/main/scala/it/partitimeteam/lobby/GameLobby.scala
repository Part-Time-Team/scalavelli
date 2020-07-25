package it.partitimeteam.lobby

import akka.actor.ActorRef
import akka.japi.Pair
import it.partitimeteam.common.{Player, Referable}

/**
 *
 * @param numberOfPlayers min number of players required to start a match
 */
class GameLobby[T <: Referable](numberOfPlayers: Int) extends Lobby[T] {

  /**
   * @inheritdoc
   */
  override val players: Seq[Pair[String, T]] = Seq()

  /**
   * @inheritdoc
   */
  override def hasEnoughPlayers: Boolean = players.length >= numberOfPlayers

  /**
   * @inheritdoc
   */
  override def extractPlayersForMatch(): Pair[Lobby[T], Option[List[Pair[String, T]]]] = ???

  /**
   * @inheritdoc
   */
  override def addPlayer(username: String, actorRef: T): Lobby[T] = ???

  /**
   * @inheritdoc
   */
  override def removePlayer(username: String): Lobby[T] = ???

}
