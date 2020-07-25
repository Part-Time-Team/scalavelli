package it.partitimeteam.lobby

import akka.actor.ActorRef
import akka.japi.Pair

object GameLobby {

  def apply(numberOfPlayers: Int): GameLobby = new GameLobby(numberOfPlayers)

}

/**
 *
 * @param numberOfPlayers min number of players required to start a match
 */
class GameLobby(numberOfPlayers: Int) extends Lobby {

  /**
   * @inheritdoc
   */
  override val players: Seq[Pair[String, ActorRef]] = Seq()

  /**
   * @inheritdoc
   */
  override def hasEnoughPlayers: Boolean = players.length >= numberOfPlayers

  /**
   * @inheritdoc
   */
  override def extractPlayersForMatch(): Pair[Lobby, Option[List[Pair[String, ActorRef]]]] = ???

  /**
   * @inheritdoc
   */
  override def addPlayer(username: String, actorRef: ActorRef): Lobby = ???

  /**
   * @inheritdoc
   */
  override def removePlayer(username: String): Lobby = ???
}
