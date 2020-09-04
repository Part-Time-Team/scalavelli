package it.parttimeteam.view.game.scenes.panes

/**
  * Actions which each view pane should implement.
  */
trait BaseGamePane {

  /**
    * Disable all the action buttons inside the pane.
    */
  def disableActions(): Unit

  /**
    * Enable all the action buttons inside the pane.
    */
  def enableActions(): Unit
}
