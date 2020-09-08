package it.parttimeteam.controller.startup

import it.parttimeteam.Constants
import it.parttimeteam.model._
import it.parttimeteam.model.startup.{GameMatchInformations, StartupService, StartupServiceImpl}
import it.parttimeteam.view.startup._
import scalafx.application.{JFXApp, Platform}

class StartUpControllerImpl extends StartUpController {

  private var startUpStage: MachiavelliStartUpStage = _
  private val startUpService: StartupService = new StartupServiceImpl(notifyEvent)
  private var startGameFunction: GameMatchInformations => Unit = _

  override def start(app: JFXApp, startGame: GameMatchInformations => Unit): Unit = {
    println("StartUpController - start()")

    Platform.runLater({
      startUpStage = MachiavelliStartUpStage(this)
      app.stage = startUpStage
    })
    startGameFunction = startGame
    this.startUpService.connect(Constants.Remote.SERVER_ADDRESS, Constants.Remote.SERVER_PORT)
  }

  override def onViewEvent(viewEvent: StartUpViewEvent): Unit = viewEvent match {
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
    case LeaveLobbyViewEvent => {
      this.startUpService.leaveLobby()
    }
    case _ =>
  }

  def notifyEvent(gameStartUpEvent: GameStartUpEvent): Unit = gameStartUpEvent match {

    case LobbyJoinedEvent => {
      startUpStage.notifyLobbyJoined()
    }

    case PrivateLobbyCreatedEvent(privateCode: String) => {
      startUpStage.notifyPrivateCode(privateCode)
      startUpStage.notifyLobbyJoined()
    }


    case LobbyJoinErrorEvent(result: String) => {
      startUpStage.notifyError(result)
    }

    case GameStartedEvent(gameInfo: GameMatchInformations) => {
      startGameFunction(gameInfo)
    }

    case _ =>

  }

  override def end(): Unit = {

  }
}
