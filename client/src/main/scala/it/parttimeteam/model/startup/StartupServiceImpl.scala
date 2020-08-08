package it.parttimeteam.model.startup

import akka.actor.ActorRef
import it.parttimeteam.messages.LobbyMessages.Connect
import it.parttimeteam.model.GameStartUpEvent
import it.parttimeteam.{ActorSystemManager, Constants}

import scala.concurrent.Future
import scala.concurrent.duration._
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global

class StartupServiceImpl(private val notifyEvent: GameStartUpEvent => Unit) extends StartupService with StartupServerResponsesListener {

  private lazy val startupActorRef = ActorSystemManager.actorSystem.actorOf(StartUpActor.props(this))
  private var serverLobbyRef: ActorRef = _
  private var clientGeneratedId: String = _

  override def connect(address: String, port: Int): Unit = {
    resolveRemoteActorPath(generateServerActorPath(address, port)) onComplete ({
      case Success(ref) => {
        ref ! Connect(startupActorRef)
      }
      case Failure(t) => {

      }

    })
  }

  override def joinPublicLobby(username: String, numberOfPlayers: Int): Unit = ???

  override def createPrivateLobby(username: String, numberOfPlayers: Int): Unit = ???

  override def joinPrivateLobby(username: String, privateLobbyId: String): Unit = ???

  override def leaveLobby(): Unit = ???

  // region server response listener

  override def connected(clientId: String, serverLobbyRef: ActorRef): Unit = {
    this.serverLobbyRef = serverLobbyRef
    this.clientGeneratedId = clientId
  }

  override def joinedToPrivateLobby(): Unit = ???

  override def joinedToPublicLobby(): Unit = ???

  override def privateLobbyCreated(privateLobbyId: String): Unit = ???

  // endregion

  private def generateServerActorPath(address: String, port: Int): String =
    s"akka.tcp://${Constants.Remote.SERVER_ACTOR_SYSTEM_NAME}@$address:$port/user/${Constants.Remote.SERVER_LOBBY_ACTOR_NAME}"

  private def resolveRemoteActorPath(actorPath: String): Future[ActorRef] = {
    ActorSystemManager.actorSystem.actorSelection(actorPath).resolveOne()(10.seconds)
  }


}
