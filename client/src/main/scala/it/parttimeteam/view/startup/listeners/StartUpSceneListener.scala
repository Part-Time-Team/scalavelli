package it.parttimeteam.view.startup.listeners

import it.parttimeteam.view.startup.StartUpViewEvent

trait StartUpSceneListener {

  def onBackPressed(): Unit

  def onSubmit(viewEvent: StartUpViewEvent): Unit
}
