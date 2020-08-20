package it.parttimeteam.controller.startup

import it.parttimeteam.view.startup.StartUpViewEvent

trait GameStartUpListener {

  def onViewEvent(viewEvent: StartUpViewEvent): Unit
}
