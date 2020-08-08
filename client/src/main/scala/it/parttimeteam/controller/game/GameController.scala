package it.parttimeteam.controller.game

import akka.actor.ActorRef
import it.parttimeteam.controller.BaseController
import it.parttimeteam.view.game.GameStageListener
import scalafx.application.JFXApp

/**
  * Controller responsible of the main game
  */
trait GameController extends BaseController with GameStageListener {
  def start(app: JFXApp, gameRef: ActorRef): Unit

}
