package it.parttimeteam.lobby

import akka.actor.{Actor, ActorLogging, ActorRef, Props, Terminated}
import it.parttimeteam.`match`.GameMatchManagerActor
import it.parttimeteam.`match`.GameMatchManagerActor.GamePlayers
import it.parttimeteam.common.{GamePlayer, IdGenerator}
import it.parttimeteam.core.GameInterfaceImpl
import it.parttimeteam.messages.LobbyMessages._
import it.parttimeteam.messages.PrivateLobbyIdNotValidError

object LobbyManagerActor {

  def props() = Props(new LobbyManagerActor())

}

class LobbyManagerActor extends Actor with IdGenerator with ActorLogging {

  type UserName = String
  type UserId = String

  private var connectedPlayers: Map[UserId, ActorRef] = Map()
  private val lobbyManger: LobbyManager[GamePlayer] = LobbyManager()

  private val privateLobbyService: PrivateLobbyService = PrivateLobbyService()


  override def receive: Receive = {
    case Connect(clientRef) => {
      val clientId = generateId
      connectedPlayers = connectedPlayers + (clientId -> clientRef)
      context.watch(clientRef)
      clientRef ! Connected(clientId)
    }

    case JoinPublicLobby(clientId, username, numberOfPlayers) => {
      this.executeOnClientRefPresent(clientId) { ref =>
        val lobbyType = PlayerNumberLobby(numberOfPlayers)
        this.lobbyManger.addPlayer(GamePlayer(clientId, username, ref), lobbyType)
        ref ! UserAddedToLobby()
        this.checkAndCreateGame(lobbyType)
      }

    }
    case CreatePrivateLobby(clientId, username, numberOfPlayers) => {
      this.executeOnClientRefPresent(clientId) { ref =>
        val lobbyType = privateLobbyService.generateNewPrivateLobby(numberOfPlayers)
        this.lobbyManger.addPlayer(GamePlayer(clientId, username, ref), lobbyType)
        ref ! PrivateLobbyCreated(lobbyType.lobbyId)
      }
    }
    case JoinPrivateLobby(clientId, username, lobbyCode) =>
      this.executeOnClientRefPresent(clientId) { ref =>
        privateLobbyService.retrieveExistingLobby(lobbyCode) match {
          case Some(lobbyType) => {
            val player = GamePlayer(clientId, username, ref)
            this.lobbyManger.addPlayer(player, lobbyType)
            ref ! UserAddedToLobby()
            this.checkAndCreateGame(lobbyType)
          }
          case None => ref ! LobbyError(PrivateLobbyIdNotValidError)
        }
      }

    case LeaveLobby(userId) => this.lobbyManger.removePlayer(userId)

    case Terminated(actorRef) => {
      log.debug(s"terminated $actorRef, connected players: $connectedPlayers")
      removeClient(actorRef)
    }

  }

  private def checkAndCreateGame(lobbyType: LobbyType): Unit = {
    this.lobbyManger.attemptExtractPlayerForMatch(lobbyType) match {
      case Some(players) => this.generateAndStartGameActor(lobbyType)(players)
      case None =>
    }
  }

  private def generateAndStartGameActor(lobbyType: LobbyType)(players: Seq[GamePlayer]): Unit = {
    val gameActor = context.actorOf(GameMatchManagerActor.props(lobbyType.numberOfPlayers, new GameInterfaceImpl()))
    players.foreach(p => {
      context.unwatch(p.actorRef)
      // remove player form lobby
      this.lobbyManger.removePlayer(p.id)
      // remove player from connected players structure
      this.connectedPlayers = this.connectedPlayers - p.id
    })
    gameActor ! GamePlayers(players)
  }

  private def getClientRef(clientId: String): Option[ActorRef] = {
    this.connectedPlayers.get(clientId)
  }

  private def executeOnClientRefPresent(clientId: String)(action: ActorRef => Unit): Unit = {
    this.getClientRef(clientId) match {
      case Some(ref) => action(ref)
      case _ =>
    }
  }

  private def removeClient(actorRef: ActorRef): Unit = {
    this.connectedPlayers.find(_._2 == actorRef) match {
      case Some((userId, _)) => {
        log.debug(s"removing client $actorRef from lobby and connected players list")
        context.unwatch(actorRef)
        this.lobbyManger.removePlayer(userId)
        this.connectedPlayers = this.connectedPlayers - userId
        log.debug(s"removed client $actorRef from lobby and connected players list")
      }
      case None => log.warning(s"client $actorRef not found")
    }
  }

}
