package it.partitimeteam.lobby

import java.util.UUID

import akka.actor.{Actor, Props}
import it.partitimeteam.common.GamePlayer
import it.parttimeteam.messages.Messages.{Connect, ConnectUserToPrivateLobby, ConnectUserToPublicLobby, LeaveLobby, LobbyConnectionAccepted, PrivateLobbyCreated, RequestPrivateLobbyCreation, UserConnectionAccepted, UserConnectionRefused}

object LobbyManagerActor {

  def props() = Props(new LobbyManagerActor())

}

class LobbyManagerActor extends Actor {

  type UserName = String
  type UserId = String

  private var connectedPlayers: Map[UserId, UserName] = Map()
  private val lobbyManger = new LobbyManagerImpl[GamePlayer]()


  override def receive: Receive = {
    case Connect(userName) => {
      val userId = UUID.randomUUID().toString
      connectedPlayers = connectedPlayers + (userId -> userName)
      sender() ! UserConnectionAccepted(userId)
    }
    case ConnectUserToPublicLobby(userId, numberOfPlayers) => {
      connectedPlayers.get(userId) match {
        case Some(username) => {
          this.lobbyManger.addPlayer(GamePlayer(userId, username, sender()), PlayerNumberLobby(numberOfPlayers))
          sender() ! LobbyConnectionAccepted
        }
        case None => sender() ! UserConnectionRefused("Invalid user id")
      }

    }
    case RequestPrivateLobbyCreation(userId, numberOfPlayers) => {
      val lobbyId = UUID.randomUUID().toString
      connectedPlayers.get(userId) match {
        case Some(username) => {
          this.lobbyManger.addPlayer(GamePlayer(userId, username, sender()), PrivateLobby(lobbyId, numberOfPlayers))
          sender() ! PrivateLobbyCreated(lobbyId)

        }
        case None => 
      }

    }
    case ConnectUserToPrivateLobby(userId, lobbyCode) =>
    case LeaveLobby(userId) => this.lobbyManger.removePlayer(userId)

  }


}
