package it.parttimeteam.controller

import it.parttimeteam.controller.game.{GameController, GameControllerImpl}
import it.parttimeteam.controller.startup.{StartupController, StartupControllerImpl}
import it.parttimeteam.model.startup.GameMatchInformations
import it.parttimeteam.view.{View, ViewImpl}
import scalafx.application.JFXApp

class MainControllerImpl(app: JFXApp) extends MainController {

  val view: View = new ViewImpl(app)
  val startUpController: StartupController = new StartupControllerImpl
  val gameController: GameController = new GameControllerImpl(() => playAgain())

  override def start(): Unit = {
    startUpController.start(app, startGame)
  }

  private def startGame(gameInfo: GameMatchInformations): Unit = {
    startUpController.end()
    gameController.start(app, gameInfo)
  }

  private def playAgain(): Unit = {
    startUpController.start(app, startGame)
    gameController.end()
  }
}
