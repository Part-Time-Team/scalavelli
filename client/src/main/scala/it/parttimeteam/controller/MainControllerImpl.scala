package it.parttimeteam.controller

import it.parttimeteam.controller.game.{GameController, GameControllerImpl}
import it.parttimeteam.controller.startup.{StartUpController, StartUpControllerImpl}
import it.parttimeteam.view.View
import it.parttimeteam.view.View.ViewImpl
import scalafx.application.JFXApp

class MainControllerImpl(app: JFXApp) extends MainController {

  val view: View = new ViewImpl(app)

  override def startGame(gameRef: String): Unit = {
    //val controller: GameContoller = new GameContollerImpl
    //view.setController(controller)
  }

  override def startUp(gameAvailable: GameAvailableCallback): Unit = {
    val controller: StartUpController = new StartUpControllerImpl
    controller.start(app)
  }
}
