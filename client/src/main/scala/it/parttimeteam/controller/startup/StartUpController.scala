package it.parttimeteam.controller.startup

import it.parttimeteam.controller.BaseController
import it.parttimeteam.model.startup.GameMatchInformations
import scalafx.application.JFXApp

/**
  * Controller responsible of the game initialization
  */
trait StartUpController extends BaseController with GameStartUpListener {

  def end() : Unit

  def start(app: JFXApp, startGame: GameMatchInformations => Unit): Unit
}
