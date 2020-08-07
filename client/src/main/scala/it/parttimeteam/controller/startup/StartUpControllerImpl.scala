package it.parttimeteam.controller.startup

import akka.actor.{ActorRef, ActorSystem}
import it.parttimeteam.messages.Messages._
import it.parttimeteam.model.actors.StartUpActor
import it.parttimeteam.model._
import it.parttimeteam.view.startup.MachiavelliStartUpPrimaryStage
import it.parttimeteam.view._
import scalafx.application.JFXApp

class StartUpControllerImpl extends StartUpController {
  val startUpStage = MachiavelliStartUpPrimaryStage(this)
  var startUpActorRef: ActorRef = _

  var startGameFunction: ActorRef => Unit = _

  override def start(app: JFXApp, startGame: ActorRef => Unit): Unit = {
    app.stage = startUpStage
    val actorSystem = ActorSystem()
    startUpActorRef = actorSystem.actorOf(StartUpActor.props(notifyEvent))
    startGameFunction = startGame
  }

  override def onViewEvent(viewEvent: ViewEvent): Unit = viewEvent match {
    case PublicGameSubmitViewEvent(username, playersNumber) => {
      System.out.println(s"PublicGameSubmitViewEvent $username - $playersNumber")
      startUpActorRef ! JoinPublicLobby(username, playersNumber)
    }
    case PrivateGameSubmitViewEvent(username, code) => {
      System.out.println(s"PrivateGameSubmitViewEvent $username - $code")
      startUpActorRef ! JoinPrivateLobby(username, code)

    }
    case CreatePrivateGameSubmitViewEvent(username, playersNumber) => {
      System.out.println(s"CreatePrivateGameSubmitViewEvent $username - $playersNumber")
      startUpActorRef ! CreatePrivateLobby(username, playersNumber)
    }
    case LeaveLobbyViewEvent(userId) => {
      System.out.println(s"LeaveLobbyViewEvent $userId")
      startUpActorRef ! LeaveLobby(userId)
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

    case GameStartedEvent(gameActorRef: ActorRef) => {
      startGameFunction(gameActorRef)
    }

    case _ => {

    }

  }

  override def end(): Unit = {
    startUpActorRef ! Stop
  }
}
