package it.parttimeteam.view.startup.listeners

import it.parttimeteam.view.startup.StartupViewEvent

/**
  * Actions which user can make inside input scenes
  */
trait StartupSceneListener {

  /**
    * The user press back button
    */
  def onBackPressed(): Unit

  /**
    * The user press submit button
    *
    * @param viewEvent the event to trigger
    */
  def onSubmit(viewEvent: StartupViewEvent): Unit
}
