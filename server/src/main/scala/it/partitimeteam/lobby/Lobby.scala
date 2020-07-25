package it.partitimeteam.lobby

import akka.actor.ActorRef
import akka.japi.Pair

trait Lobby {

  val players: Seq[Pair[String, ActorRef]]

  /**
   * Check if the current lobby ha enough players to start a game
   *
   * @return
   */
  def hasEnoughPlayers: Boolean

  /**
   * Extract the correct number of players to start a match
   *
   * @return the new lobby without the extracted player and the extracted players if present, otherwise none
   */
  def extractPlayersForMatch(): Pair[Lobby, Option[List[Pair[String, ActorRef]]]]

  /**
   * Add a new player to the lobby
   *
   * @param username username of the player
   * @param actorRef actor ref of the remote player
   * @return a new lobby with the new player
   */
  def addPlayer(username: String, actorRef: ActorRef): Lobby

  /**
   * Remove a player from the lobby
   *
   * @param username of the player to remove
   * @return a new lobby without the player
   */
  def removePlayer(username: String): Lobby

}

