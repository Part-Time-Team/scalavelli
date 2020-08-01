package it.partitimeteam.lobby

import akka.actor.{Actor, Props}
import it.partitimeteam.common.IdGenerator
import it.parttimeteam.entities
import it.parttimeteam.entities.{GamePlayer, Player}
import it.parttimeteam.messages.LobbyMessages._

object LobbyManagerActor {

  def props() = Props(new LobbyManagerActor())

}

class LobbyManagerActor extends Actor with IdGenerator {

  type UserName = String
  type UserId = String

  private var connectedPlayers: Map[UserId, UserName] = Map()
  private val lobbyManger: LobbyManager[GamePlayer] = new LobbyManagerImpl[GamePlayer]()

  private val privateLobbyService: PrivateLobbyService = PrivateLobbyService()


  override def receive: Receive = {
    case JoinPublicLobby(username, numberOfPlayers) => {
      val playerId = this.generateId
      this.lobbyManger.addPlayer(entities.GamePlayer(playerId, username, sender()), PlayerNumberLobby(numberOfPlayers))
      sender() ! UserAddedToLobby(playerId)

    }
    case CreatePrivateLobby(username, numberOfPlayers) => {
      val lobby = privateLobbyService.generateNewPrivateLobby(numberOfPlayers)
      val playerId = this.generateId
      this.lobbyManger.addPlayer(entities.GamePlayer(playerId, username, sender()), lobby)
      sender() ! PrivateLobbyCreated(playerId, lobby.lobbyId)

    }
    case JoinPrivateLobby(username, lobbyCode) => privateLobbyService.retrieveExistingLobby(lobbyCode) match {
      case Some(lobby) => {
        val player = entities.GamePlayer(generateId, username, sender())
        this.lobbyManger.addPlayer(player, lobby)
        sender() ! UserAddedToLobby(player.id)
      }
      case None => sender() ! LobbyJoinError(s"Private lobby with code $lobbyCode does not exist")
    }

    case LeaveLobby(userId) => this.lobbyManger.removePlayer(userId)

  }

}
