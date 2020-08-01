package it.parttimeteam.controller.startup

import akka.actor.{ActorRef, ActorSystem}
import it.parttimeteam.messages.Messages.{CreatePrivateLobby, JoinPrivateLobby, JoinPublicLobby, LeaveLobby}
import it.parttimeteam.model.actors.StartUpActor
import it.parttimeteam.model._
import it.parttimeteam.view.startup.MachiavelliStartUpPrimaryStage
import it.parttimeteam.view._
import scalafx.application.JFXApp

class StartUpControllerImpl extends StartUpController {
  val startUpStage = MachiavelliStartUpPrimaryStage(this)
  var  actorRef: ActorRef = _

  override def start(app: JFXApp): Unit = {
    app.stage = startUpStage
    val actorSystem = ActorSystem()
    actorRef = actorSystem.actorOf(StartUpActor.props(notifyEvent))
  }

  override def onViewEvent(viewEvent: ViewEvent): Unit = viewEvent match {
    case PublicGameSubmitViewEvent(username, playersNumber) => {
      System.out.println(s"PublicGameSubmitViewEvent $username - $playersNumber")
      actorRef ! JoinPublicLobby(username, playersNumber)
    }
    case PrivateGameSubmitViewEvent(username, code) => {
      System.out.println(s"PrivateGameSubmitViewEvent $username - $code")
      actorRef ! JoinPrivateLobby(username, code)

    }
    case CreatePrivateGameSubmitViewEvent(username, playersNumber) => {
      System.out.println(s"CreatePrivateGameSubmitViewEvent $username - $playersNumber")
      actorRef ! CreatePrivateLobby(username, playersNumber)
    }
    case LeaveLobbyViewEvent(userId) => {
      System.out.println(s"LeaveLobbyViewEvent $userId")
      actorRef ! LeaveLobby(userId)
    }
    case _ =>
  }

  def notifyEvent(gameStartUpEvent: GameStartUpEvent) : Unit = gameStartUpEvent match {
    case LobbyJoinedEvent(userId: String) => {
      startUpStage.notifyLobbyJoined()
    }
    case PrivateLobbyCreatedEvent(userId: String, privateCode: String) => {
      startUpStage.notifyPrivateCode(privateCode)
      startUpStage.notifyLobbyJoined()
    }
    case PrivateLobbyCreatedEvent(userId: String, privateCode: String) => {
      startUpStage.notifyPrivateCode(privateCode)
      startUpStage.notifyLobbyJoined()
    }
    case LobbyJoinErrorEvent(result: String) => {
      startUpStage.notifyError(result)
    }
    case GameStartedEvent(gameActorRef: ActorRef) => ??? // TODO: Menage game start
    case _ => ???

  }
}
