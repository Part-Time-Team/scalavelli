package it.parttimeteam

import it.parttimeteam.controller.{GameAvailableCallback, MainController, MainControllerImpl}
import scalafx.application.JFXApp

object AppLauncher extends JFXApp {
  val controller: MainController = new MainControllerImpl(this)

  controller.startUp((gameRef: String) => {
    controller.startGame(gameRef)
  })

  // TODO: Luca - Alla callback: controller.startGame()
}

