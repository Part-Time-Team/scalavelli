package it.parttimeteam.controller.startup

import akka.actor.ActorRef
import it.parttimeteam.model._
import it.parttimeteam.model.startup.{StartupService, StartupServiceImpl}
import it.parttimeteam.view._
import it.parttimeteam.view.startup.MachiavelliStartUpPrimaryStage
import scalafx.application.JFXApp

class StartUpControllerImpl extends StartUpController {
  val startUpStage = MachiavelliStartUpPrimaryStage(this)

  private val startUpService: StartupService = new StartupServiceImpl(notifyEvent)

  var startGameFunction: ActorRef => Unit = _

  override def start(app: JFXApp, startGame: ActorRef => Unit): Unit = {
    app.stage = startUpStage
    startGameFunction = startGame
    this.startUpService.connect("localhost", 5150)
  }

  override def onViewEvent(viewEvent: ViewEvent): Unit = viewEvent match {
    case PublicGameSubmitViewEvent(username, playersNumber) => {
      System.out.println(s"PublicGameSubmitViewEvent $username - $playersNumber")
      this.startUpService.joinPublicLobby(username, playersNumber)
    }
    case PrivateGameSubmitViewEvent(username, code) => {
      System.out.println(s"PrivateGameSubmitViewEvent $username - $code")
      this.startUpService.joinPrivateLobby(username, code)
    }
    case CreatePrivateGameSubmitViewEvent(username, playersNumber) => {
      System.out.println(s"CreatePrivateGameSubmitViewEvent $username - $playersNumber")
      this.startUpService.createPrivateLobby(username, playersNumber)
    }
    case LeaveLobbyViewEvent(userId) => {
      System.out.println(s"LeaveLobbyViewEvent $userId")
      this.startUpService.leaveLobby()
    }
    case _ =>
  }

  def notifyEvent(gameStartUpEvent: GameStartUpEvent): Unit = gameStartUpEvent match {

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

  }
}
