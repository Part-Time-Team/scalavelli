package it.parttimeteam.messages

import akka.actor.ActorRef

object Messages {

  /**
   * Message sent by the client to connect to the server
   *
   * @param username username choosed by the user
   */
  case class Connect(username: String)

  /**
   * Message sent by the client to join a public lobby for a match with the given number of players
   *
   * @param userId id of the user (the one retrieved by the server after the first connection)
   * @param numberOfPlayers
   */
  case class ConnectUserToPublicLobby(userId: String, numberOfPlayers: Int)

  /**
   * Message sent by the client to join a private lobby
   *
   * @param userId id of the user (the one retrieved by the server after the first connection)
   * @param privateLobbyCode
   */
  case class ConnectUserToPrivateLobby(userId: String, privateLobbyCode: String)

  /**
   * Message sent by the client to request a creation of a new private lobby
   *
   * @param userId id of the user (the one retrieved by the server after the first connection)
   * @param numberOfPlayers
   */
  case class RequestPrivateLobbyCreation(userId: String, numberOfPlayers: Int)

  /**
   *
   * @param userId id of the user (the one retrieved by the server after the first connection)
   */
  case class LeaveLobby(userId: String)


  /**
   *
   *
   * @param userId a server generated user id
   */
  case class UserConnectionAccepted(userId: String)

  /**
   *
   * @param reason description of the error
   */
  case class UserConnectionRefused(reason: String)

  /**
   * Message sent by the server after a successfull lobby connection
   *
   */
  case object LobbyConnectionAccepted

  /**
   * Message sent by the server on match found
   *
   * @param gameRoom actorRef of the game room to join
   */
  case class MatchFound(gameRoom: ActorRef)


  /**
   * Message sent by the server after private lobby creation
   *
   * @param lobbyCode code of the created lobby
   */
  case class PrivateLobbyCreated(lobbyCode: String)

}
