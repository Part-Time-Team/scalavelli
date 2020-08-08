package it.parttimeteam.messages

import akka.actor.ActorRef

object LobbyMessages {

  /**
   * Message sent by the client to join a public lobby for a match with the given number of players
   *
   * @param username        username choosed by the user
   * @param numberOfPlayers required to start a match
   */
  case class JoinPublicLobby(username: String, numberOfPlayers: Int)

  /**
   * Message sent by the client to join a private lobby
   *
   * @param username         username choosed by the user
   * @param privateLobbyCode required to start a match
   */
  case class JoinPrivateLobby(username: String, privateLobbyCode: String)

  /**
   * Message sent by the client to request a creation of a new private lobby and join
   *
   * @param username        username choosed by the user
   * @param numberOfPlayers required to start a match
   */
  case class CreatePrivateLobby(username: String, numberOfPlayers: Int)

  /**
   * Message sent by the client to leave the current lobby
   *
   * @param userId id of the user (the one retrieved by the server after the first connection)
   */
  case class LeaveLobby(userId: String)


  /**
   * Message sent by the server after a successfull lobby connection
   *
   * @param generatedUserId the id of the user generated by the server
   */
  case class UserAddedToLobby(generatedUserId: String)

  case class LobbyJoinError(reason: String)

  /**
   * Message sent by the server after private lobby creation
   *
   * @param generatedUserId the id of the user generated by the server
   * @param lobbyCode       code of the created lobby
   */
  case class PrivateLobbyCreated(generatedUserId: String, lobbyCode: String)


  /**
   * Message sent by the server on match found
   *
   * @param gameRoom actorRef of the game room to join
   */
  case class MatchFound(gameRoom: ActorRef)


}