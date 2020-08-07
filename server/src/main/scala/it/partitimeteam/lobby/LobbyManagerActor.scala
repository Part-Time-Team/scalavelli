package it.partitimeteam.lobby

import akka.actor.{Actor, Props}
import it.partitimeteam.`match`.GameMatchActor
import it.partitimeteam.common.IdGenerator
import it.parttimeteam.entities
import it.parttimeteam.entities.GamePlayer
import it.parttimeteam.messages.GameMessage.GamePlayers
import it.parttimeteam.messages.LobbyMessages._

object LobbyManagerActor {

  def props() = Props(new LobbyManagerActor())

}

class LobbyManagerActor extends Actor with IdGenerator {

  type UserName = String
  type UserId = String

  private var connectedPlayers: Map[UserId, UserName] = Map()
  private val lobbyManger: LobbyManager[GamePlayer] = LobbyManager()

  private val privateLobbyService: PrivateLobbyService = PrivateLobbyService()


  override def receive: Receive = {
    case JoinPublicLobby(username, numberOfPlayers) => {
      val playerId = this.generateId
      val lobbyType = PlayerNumberLobby(numberOfPlayers)
      this.lobbyManger.addPlayer(entities.GamePlayer(playerId, username, sender()), lobbyType)
      sender() ! UserAddedToLobby(playerId)
      this.checkAndCreateGame(lobbyType)

    }
    case CreatePrivateLobby(username, numberOfPlayers) => {
      val lobbyType = privateLobbyService.generateNewPrivateLobby(numberOfPlayers)
      val playerId = this.generateId
      this.lobbyManger.addPlayer(entities.GamePlayer(playerId, username, sender()), lobbyType)
      sender() ! PrivateLobbyCreated(playerId, lobbyType.lobbyId)

    }
    case JoinPrivateLobby(username, lobbyCode) => privateLobbyService.retrieveExistingLobby(lobbyCode) match {
      case Some(lobbyType) => {
        val player = entities.GamePlayer(generateId, username, sender())
        this.lobbyManger.addPlayer(player, lobbyType)
        sender() ! UserAddedToLobby(player.id)
        this.checkAndCreateGame(lobbyType)
      }
      case None => sender() ! LobbyJoinError(s"Private lobby with code $lobbyCode does not exist")
    }

    case LeaveLobby(userId) => this.lobbyManger.removePlayer(userId)

  }

  private def checkAndCreateGame(lobbyType: LobbyType): Unit = {
    this.lobbyManger.attemptExtractPlayerForMatch(lobbyType) match {
      case Some(players) => this.generateAndStartGameActor(lobbyType)(players)
      case None =>
    }
  }

  private def generateAndStartGameActor(lobbyType: LobbyType)(players: Seq[GamePlayer]): Unit = {
    val gameActor = context.actorOf(GameMatchActor.props(lobbyType.numberOfPlayers))
    gameActor ! GamePlayers(players)
  }

}
