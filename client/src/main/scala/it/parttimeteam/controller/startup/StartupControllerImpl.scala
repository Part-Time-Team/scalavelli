package it.parttimeteam.controller.startup

import it.parttimeteam.Constants
import it.parttimeteam.model.ErrorEvent
import it.parttimeteam.model.startup._
import it.parttimeteam.view.startup._
import scalafx.application.{JFXApp, Platform}

class StartupControllerImpl extends StartupController {

  private var startUpStage: StartupStage = _
  private val startUpService: StartupService = new StartupServiceImpl(notifyEvent)
  private var startGameFunction: GameMatchInformations => Unit = _

  override def start(app: JFXApp, startGame: GameMatchInformations => Unit): Unit = {
    Platform.runLater({
      startUpStage = StartupStage(this)
      app.stage = startUpStage
    })
    startGameFunction = startGame
    this.startUpService.connect(Constants.Remote.SERVER_ADDRESS, Constants.Remote.SERVER_PORT)
  }

  override def onViewEvent(viewEvent: StartupViewEvent): Unit = viewEvent match {

    case PublicGameSubmitViewEvent(username, playersNumber) => this.startUpService.joinPublicLobby(username, playersNumber)

    case PrivateGameSubmitViewEvent(username, code) => this.startUpService.joinPrivateLobby(username, code)

    case CreatePrivateGameSubmitViewEvent(username, playersNumber) => this.startUpService.createPrivateLobby(username, playersNumber)

    case LeaveLobbyViewEvent => this.startUpService.leaveLobby()

    case _ =>
  }

  def notifyEvent(gameStartupEvent: GameStartupEvent): Unit = gameStartupEvent match {

    case LobbyJoinedEvent => startUpStage.notifyLobbyJoined()

    case PrivateLobbyCreatedEvent(privateCode: String) => {
      startUpStage.notifyPrivateCode(privateCode)
      startUpStage.notifyLobbyJoined()
    }

    case LobbyJoinErrorEvent(error: ErrorEvent) => startUpStage.notifyError(error)

    case GameStartedEvent(gameInfo: GameMatchInformations) => startGameFunction(gameInfo)

    case _ =>

  }

  override def end(): Unit = {

  }
}
