package it.parttimeteam.controller.startup

import it.parttimeteam.view.ViewEvent

trait GameStartUpListener {

  def onViewEvent(viewEvent: ViewEvent): Unit
}
