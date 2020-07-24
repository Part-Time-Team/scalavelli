import akka.actor.ActorRef

object Messages {

   //client requests
  case class ConnectUser(userName: String, numberOfPlayers: Int)
  case class ConnectUserToPrivateLobby(userName: String, privateLobbyCode: String)
  case class RequestPrivateLobbyCreation(numberOfPlayers: Int)
  case object LeaveLobby

  // server responses
  case object UserConnectionAccepted
  case class UserConnectionRefused(reason: String)
  case class MatchFound(gameRoom: ActorRef)
  case class PrivateLobbyCreated(lobbyCode: String)

}
