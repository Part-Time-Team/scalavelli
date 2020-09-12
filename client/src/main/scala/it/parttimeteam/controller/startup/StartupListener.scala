package it.parttimeteam.controller.startup

import it.parttimeteam.view.startup.StartupViewEvent

trait StartupListener {

  def onViewEvent(viewEvent: StartupViewEvent): Unit
}
