package it.partitimeteam.lobby

import akka.actor.ActorRef
import it.partitimeteam.common.Referable

trait LobbyManager[T <: Referable] {

  /**
   *
   * @param username  username of the client to add
   * @param lobbyType type of the lobby
   * @param actorRef  ActorRef of the client actor
   * @return
   */
  def addClient(username: String, lobbyType: LobbyType, actorRef: ActorRef): LobbyManager[T]

  /**
   *
   * @param username
   * @return
   */
  def removeClient(username: String): LobbyManager[T]

  /**
   *
   * @param lobbyType
   * @return the lobbing corresponding the the specified type, if present
   */
  def getLobby(lobbyType: LobbyType): Option[Lobby[T]]

}
