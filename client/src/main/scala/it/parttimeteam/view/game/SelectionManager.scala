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

  def apply[T <: SelectableItem](allowOnlyOne: Boolean): SelectionManager[T] = new SelectionManagerImpl[T](allowOnlyOne)

  class SelectionManagerImpl[T <: SelectableItem](allowOnlyOne: Boolean, private var selectedItems: Seq[T] = List.empty) extends SelectionManager[T] {
    /** @inheritdoc */
    override def onItemSelected(item: T): Unit = {
      if (allowOnlyOne) {
        this.clearSelection()
        this.addItem(item)
      } else {
        if (selectedItems.contains(item)) {
          this.removeItem(item)
        } else {
          this.addItem(item)
        }
      }

      println(s"Selected cards: ${getSelectedItems.toString}")
    }

    /** @inheritdoc */
    override def getSelectedItems: Seq[T] = selectedItems

    /** @inheritdoc */
    override def isSelectionEmpty: Boolean = selectedItems.isEmpty

    /** @inheritdoc */
    override def clearSelection(): Unit = {
      unselectAll()
      selectedItems = List.empty
    }

    private def unselectAll(): Unit = {
      for (item: SelectableItem <- selectedItems) {
        item.setSelected(false)
      }
    }

    private def removeItem(item: T): Unit = {
      selectedItems = selectedItems.filter(i => i != item)
      item.setSelected(false)
    }

    private def addItem(item: T): Unit = {
      selectedItems = selectedItems :+ item
      item.setSelected(true)
    }
  }
}
