package it.parttimeteam.controller.startup

import akka.actor.{ActorRef, ActorSystem}
import it.parttimeteam.model.actors.StartUpActor
import it.parttimeteam.model.{GameStartUpEvent, GameStarted, LobbyJoinedEvent, PrivateLobbyCreated}
import it.parttimeteam.view.startup.MachiavelliStartUpPrimaryStage
import it.parttimeteam.view.{CreatePrivateGameSubmitViewEvent, PrivateGameSubmitViewEvent, PublicGameSubmitViewEvent, ViewEvent}
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
      // TODO: Send message to actor - actorRef ! Message(username, playersNumber)
    }
    case PrivateGameSubmitViewEvent(username, code) => {
      System.out.println(s"PrivateGameSubmitViewEvent $username - $code")
      // TODO: Send message to actor - actorRef ! Message(username, code)

    }
    case CreatePrivateGameSubmitViewEvent(username, playersNumber) => {
      System.out.println(s"CreatePrivateGameSubmitViewEvent $username - $playersNumber")
      // TODO: Send message to actor - actorRef ! Message(username, playersNumber)

    }
    case _ =>
  }

  def notifyEvent(gameStartUpEvent: GameStartUpEvent) : Unit = gameStartUpEvent match {
    case LobbyJoinedEvent(userId: String) => {
      startUpStage.notifyLobbyJoined()
    }
    case PrivateLobbyCreated(userId: String, privateCode: String) => {
      startUpStage.notifyPrivateCode(privateCode)
      startUpStage.notifyLobbyJoined()
    }

    case GameStarted(gameActorRef: ActorRef) => ??? // TODO: Menage game start
    case _ => ???

  }
}
