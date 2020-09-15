package it.parttimeteam.controller.startup

import it.parttimeteam.controller.BaseController
import it.parttimeteam.model.startup.GameMatchInformations
import scalafx.application.JFXApp

/**
  * Controller responsible of the game initialization
  */
trait StartupController extends BaseController with StartupListener {

  def start(app: JFXApp, startGame: GameMatchInformations => Unit): Unit
}
