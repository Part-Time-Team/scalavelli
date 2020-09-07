package it.parttimeteam.model.startup

import akka.actor.ActorRef
import it.parttimeteam.messages.LobbyMessages._
import it.parttimeteam.model.{GameStartUpEvent, GameStartedEvent, LobbyJoinErrorEvent, LobbyJoinedEvent, PrivateLobbyCreatedEvent}
import it.parttimeteam.{ActorSystemManager, Constants}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration._
import scala.util.{Failure, Success}

class StartupServiceImpl(private val notifyEvent: GameStartUpEvent => Unit) extends StartupService {

  private lazy val startupActorRef = ActorSystemManager.actorSystem.actorOf(StartUpActor.props(serverResponseListener), "client-lobby")
  private var serverLobbyRef: ActorRef = _
  private var clientGeneratedId: String = _

  private val serverResponseListener = new StartupServerResponsesListener {
    override def connected(clientId: String, serverLobbyRef: ActorRef): Unit = {
      StartupServiceImpl.this.serverLobbyRef = serverLobbyRef
      clientGeneratedId = clientId
    }

    override def addedToLobby(): Unit = {
      notifyEvent(LobbyJoinedEvent)
    }

    override def privateLobbyCreated(privateLobbyId: String): Unit = notifyEvent(PrivateLobbyCreatedEvent(privateLobbyId))

    override def matchFound(matchRef: ActorRef): Unit =
      notifyEvent(GameStartedEvent(GameMatchInformations(clientGeneratedId, matchRef)))

  }

  override def connect(address: String, port: Int): Unit = {
    resolveRemoteActorPath(generateServerActorPath(address, port)) onComplete {
      case Success(ref) => {
        ref ! Connect(startupActorRef)
      }
      case Failure(t) => {
        // TODO MATTEOC notify
        this.notifyEvent(LobbyJoinErrorEvent("Server not found error"))
      }

    }
  }

  override def joinPublicLobby(username: String, numberOfPlayers: Int): Unit =
    serverLobbyRef ! JoinPublicLobby(clientGeneratedId, username, numberOfPlayers)

  override def createPrivateLobby(username: String, numberOfPlayers: Int): Unit =
    serverLobbyRef ! CreatePrivateLobby(clientGeneratedId, username, numberOfPlayers)

  override def joinPrivateLobby(username: String, privateLobbyId: String): Unit =
    serverLobbyRef ! JoinPrivateLobby(clientGeneratedId, username, privateLobbyId)

  override def leaveLobby(): Unit = serverLobbyRef ! LeaveLobby(clientGeneratedId)

  //  case JoinPublicLobby => notifyEvent(LobbyJoinedEvent(""))
  //  case PrivateLobbyCreatedEvent(generatedUserId: String, lobbyCode: String) => notifyEvent(PrivateLobbyCreatedEvent(generatedUserId, lobbyCode))
  //  case MatchFound(gameRoom: ActorRef) => notifyEvent(GameStartedEvent(gameRoom))
  //  case LobbyJoinError(reason: String) => notifyEvent(LobbyJoinErrorEvent(reason))
  //  case Stop() => context.stop(self)
  //  case _ =>

  // endregion

  private def generateServerActorPath(address: String, port: Int): String =
    s"akka.tcp://${Constants.Remote.SERVER_ACTOR_SYSTEM_NAME}@$address:$port/user/${Constants.Remote.SERVER_LOBBY_ACTOR_NAME}"

  /**
   * obtains the ActorRef of the corresponding remote actor
   */
  private def resolveRemoteActorPath(actorPath: String): Future[ActorRef] = {
    ActorSystemManager.actorSystem.actorSelection(actorPath).resolveOne()(10.seconds)
  }


}
