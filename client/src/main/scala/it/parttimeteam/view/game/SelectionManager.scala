package it.parttimeteam.view.game

import it.parttimeteam.view.game.scenes.model.SelectableItem

/**
  * Menages items selection and deselection.
  *
  * @tparam T a generic SelectableItem
  */
trait SelectionManager[T <: SelectableItem] {

  /**
    * Clears all items selected.
    */
  def clearSelection(): Unit

  /**
    * Called when an item is selected.
    *
    * @param item the selected item
    */
  def onItemSelected(item: T): Unit

  /**
    * Get all the selected items
    *
    * @return selected items
    */
  def getSelectedItems: Seq[T]

  /**
    * Returns if there is any selected item.
    *
    * @return if there is any selected item
    */
  def isSelectionEmpty: Boolean
}

object SelectionManager {

  def apply[T <: SelectableItem](): SelectionManager[T] = new SelectionManagerImpl[T]()

  class SelectionManagerImpl[T <: SelectableItem](private var selectedItems: Seq[T] = List.empty) extends SelectionManager[T] {
    /** @inheritdoc*/
    override def onItemSelected(item: T): Unit = {
      if (selectedItems.contains(item)) {
        selectedItems = selectedItems.filter(i => i != item)
        item.setSelected(false)
      } else {
        selectedItems = selectedItems :+ item
        item.setSelected(true)
      }

      println(s"Selected cards: ${getSelectedItems.toString}")
    }

    /** @inheritdoc*/
    override def getSelectedItems: Seq[T] = selectedItems

    /** @inheritdoc*/
    override def isSelectionEmpty: Boolean = selectedItems.isEmpty

    /** @inheritdoc*/
    override def clearSelection(): Unit = {
      for (item: SelectableItem <- selectedItems) {
        item.setSelected(false)
      }

      selectedItems = List.empty
    }
  }

}
