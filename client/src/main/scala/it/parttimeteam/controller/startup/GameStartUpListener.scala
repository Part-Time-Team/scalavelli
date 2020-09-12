package it.parttimeteam.controller.startup

import it.parttimeteam.view.startup.StartupViewEvent

trait GameStartUpListener {

  def onViewEvent(viewEvent: StartupViewEvent): Unit
}
