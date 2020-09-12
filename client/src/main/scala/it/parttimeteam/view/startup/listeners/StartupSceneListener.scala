package it.parttimeteam.view.startup.listeners

import it.parttimeteam.view.startup.StartupViewEvent

trait StartupSceneListener {

  def onBackPressed(): Unit

  def onSubmit(viewEvent: StartupViewEvent): Unit
}
