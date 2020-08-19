package it.parttimeteam.controller.game

import it.parttimeteam.controller.BaseController
import it.parttimeteam.model.startup.GameMatchInformations
import it.parttimeteam.view.game.GameStageListener
import scalafx.application.JFXApp

/**
 * Controller responsible of the main game
 */
trait GameController extends BaseController with GameStageListener {
  def start(app: JFXApp, gameInfo: GameMatchInformations): Unit

}
