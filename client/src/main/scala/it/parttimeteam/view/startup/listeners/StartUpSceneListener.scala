package it.parttimeteam.view.startup.listeners

import it.parttimeteam.view.ViewEvent

trait StartUpSceneListener {

  def onBackPressed(): Unit

  def onSubmit(viewEvent: ViewEvent): Unit
}
