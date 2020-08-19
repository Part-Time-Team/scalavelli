package it.parttimeteam.controller.game

import it.parttimeteam.view.ViewEvent

trait GameListener {

  def onViewEvent(viewEvent: ViewEvent): Unit
}
