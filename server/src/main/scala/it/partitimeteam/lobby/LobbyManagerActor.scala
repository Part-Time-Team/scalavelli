package it.partitimeteam.lobby

import akka.actor.{Actor, Props}
import it.partitimeteam.common.{GamePlayer, IdGenerator}
import it.parttimeteam.messages.Messages._

object LobbyManagerActor {

  def props() = Props(new LobbyManagerActor())

}

class LobbyManagerActor extends Actor with IdGenerator {

  type UserName = String
  type UserId = String

  private var connectedPlayers: Map[UserId, UserName] = Map()
  private val lobbyManger = new LobbyManagerImpl[GamePlayer]()


  override def receive: Receive = {
    case JoinPublicLobby(username, numberOfPlayers) => {
      val playerId = this.generateId
      this.lobbyManger.addPlayer(GamePlayer(playerId, username, sender()), PlayerNumberLobby(numberOfPlayers))
      sender() ! UserAddedToLobby(playerId)

    }
    case CreatePrivateLobby(username, numberOfPlayers) => {
      val lobbyId = this.generateId
      val playerId = this.generateId
      this.lobbyManger.addPlayer(GamePlayer(playerId, username, sender()), PrivateLobby(lobbyId, numberOfPlayers))
      sender() ! PrivateLobbyCreated(playerId, lobbyId)

    }
    case JoinPrivateLobby(userId, lobbyCode) =>

    case LeaveLobby(userId) => this.lobbyManger.removePlayer(userId)

  }

}
