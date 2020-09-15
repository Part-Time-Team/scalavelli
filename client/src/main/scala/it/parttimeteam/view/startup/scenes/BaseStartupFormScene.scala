package it.parttimeteam.view.startup.scenes

/**
  * Actions which each input scene must implement
  */
trait BaseStartupFormScene {
  /**
    * Display a message inside the scene
    *
    * @param message the message to be displayed
    */
  def showMessage(message: String): Unit

  /**
    * Hide the message inside the scene
    */
  def hideMessage(): Unit

  /**
    * Disable actions inside the view
    */
  def disableActions(): Unit

  /**
    * Enable actions inside the view
    */
  def enableActions(): Unit

  /**
    * Clear user input and elements visibility
    */
  def resetScreen(): Unit
}
