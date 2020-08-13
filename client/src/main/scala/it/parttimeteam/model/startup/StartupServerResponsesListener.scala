package it.parttimeteam.model.startup

import akka.actor.ActorRef

/**
 * Callback functions for server responses
 */
trait StartupServerResponsesListener {

  /**
   * Callback on service connection successfull
   *
   * @param clientId       client id generate by the server
   * @param serverLobbyRef server lobby actor ref
   */
  def connected(clientId: String, serverLobbyRef: ActorRef)

  /**
   * Notify the successfully join to the lobby (private or public)
   */
  def addedToLobby()

  /**
   * Notify the successfully creation of the private lobby
   *
   * @param privateLobbyId id of the created lobby
   */
  def privateLobbyCreated(privateLobbyId: String)

  /**
   * Callback on match found
   *
   * @param matchRef the game match ref, used later to interact with the server
   */
  def matchFound(matchRef: ActorRef)

  // TODO error

}
