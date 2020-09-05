package it.parttimeteam.controller

import it.parttimeteam.controller.game.{GameController, GameControllerImpl}
import it.parttimeteam.controller.startup.{StartUpController, StartUpControllerImpl}
import it.parttimeteam.model.startup.GameMatchInformations
import it.parttimeteam.view.{View, ViewImpl}
import scalafx.application.JFXApp

class MainControllerImpl(app: JFXApp) extends MainController {

  val view: View = new ViewImpl(app)
  val startUpController: StartUpController = new StartUpControllerImpl
  val gameController: GameController = new GameControllerImpl(() => playAgain())

  override def start(): Unit = {
    startUpController.start(app, startGame)
  }

  def startGame(gameInfo: GameMatchInformations): Unit = {
    startUpController.end()
    gameController.start(app, gameInfo)
  }

  def playAgain(): Unit = {
    startUpController.start(app, startGame)
    gameController.end()
  }
}
