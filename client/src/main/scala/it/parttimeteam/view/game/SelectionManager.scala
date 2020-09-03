package it.parttimeteam.view.game

import it.parttimeteam.view.game.scenes.model.SelectableItem

trait SelectionManager[T <: SelectableItem] {
  def clearSelection(): Unit

  def onItemSelected(item: T): Unit

  def getSelectedItems: Seq[T]

  def isSelectionEmpty: Boolean
}

object SelectionManager {

  def apply[T <: SelectableItem](): SelectionManager[T] = new SelectionManagerImpl[T]()

  private class SelectionManagerImpl[T <: SelectableItem](private var selectedItems: Seq[T] = List.empty) extends SelectionManager[T] {
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

    override def getSelectedItems: Seq[T] = selectedItems

    override def isSelectionEmpty: Boolean = selectedItems.isEmpty

    override def clearSelection(): Unit = {
      for (item: SelectableItem <- selectedItems) {
        item.setSelected(false)
      }

      selectedItems = List.empty
    }
  }

}
