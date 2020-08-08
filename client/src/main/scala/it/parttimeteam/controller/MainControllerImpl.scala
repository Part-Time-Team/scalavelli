package it.parttimeteam.controller

import akka.actor.ActorRef
import it.parttimeteam.controller.game.{GameController, GameControllerImpl}
import it.parttimeteam.controller.startup.{StartUpController, StartUpControllerImpl}
import it.parttimeteam.view.{View, ViewImpl}
import scalafx.application.JFXApp

class MainControllerImpl(app: JFXApp) extends MainController {

  val view: View = new ViewImpl(app)
  val startUpController: StartUpController = new StartUpControllerImpl
  val gameController: GameController = new GameControllerImpl

  override def start(): Unit = {
    startUpController.start(app, startGame)
    // TODO: Luca - rimuovere quando funziona avvio game
    // gameController.start(app, null)
  }

  def startGame(gameRef: ActorRef) : Unit = {
    startUpController.end()
    gameController.start(app, gameRef)
  }
}
