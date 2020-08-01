package it.parttimeteam

import it.parttimeteam.controller.{MainController, MainControllerImpl}
import scalafx.application.JFXApp

/**
  * The class which initialize the whole client application
  */
object AppLauncher extends JFXApp {
  val controller: MainController = new MainControllerImpl(this)

  // TODO: LUCA - replace gameRef type with the correct one
  controller.startUp((gameRef: String) => {
    controller.startGame(gameRef)
  })
}

