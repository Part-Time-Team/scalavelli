package it.parttimeteam.controller.game

import it.parttimeteam.view.game.ViewGameEvent

trait GameListener {

  def onViewEvent(viewEvent: ViewGameEvent): Unit
}
