package it.parttimeteam.view.game.scenes.panes

/**
  * Expose methods which each node which allows user interaction should implement.
  */
trait ActionGamePane {

  /**
    * Disable actions inside the pane.
    */
  def disableActions(): Unit

  /**
    * Enable actions inside the pane.
    */
  def enableActions(): Unit
}
