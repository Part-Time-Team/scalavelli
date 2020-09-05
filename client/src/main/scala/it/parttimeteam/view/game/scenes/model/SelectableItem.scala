package it.parttimeteam.view.game.scenes.model

/**
  * Object which can be selected by SelectionManager.
  */
trait SelectableItem {

  /**
    * Allows to select or deselect an item.
    *
    * @param selected if the card should be selected or not
    */
  def setSelected(selected: Boolean): Unit
}
