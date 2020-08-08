package it.parttimeteam.controller.startup

import akka.actor.ActorRef
import it.parttimeteam.controller.BaseController
import scalafx.application.JFXApp

/**
  * Controller responsible of the game initialization
  */
trait StartUpController extends BaseController with GameStartUpListener {

  def end() : Unit

  def start(app: JFXApp, startGame: ActorRef => Unit): Unit
}
