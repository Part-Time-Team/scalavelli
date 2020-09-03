package it.parttimeteam.view.game

import it.parttimeteam.view.game.scenes.model.PlayerCard

trait SelectionManager {
  def clearSelection(): Unit

  def onItemSelected(item: PlayerCard): Unit

  def getSelectedItems: Seq[PlayerCard]

  def isSelectionEmpty: Boolean
}

object SelectionManager {
  def apply(): SelectionManager = new SelectionManagerImpl()

  private class SelectionManagerImpl(private var selectedItems: Seq[PlayerCard] = List.empty) extends SelectionManager {
    override def onItemSelected(item: PlayerCard): Unit = {
      if (selectedItems.contains(item)) {
        selectedItems = selectedItems.filter(i => i != item)
        item.setSelected(false)
      } else {
        selectedItems = selectedItems :+ item
        item.setSelected(true)
      }

      println(s"Selected cards: ${getSelectedItems.toString}")
    }

    override def getSelectedItems: Seq[PlayerCard] = selectedItems

    override def isSelectionEmpty: Boolean = selectedItems.isEmpty

    override def clearSelection(): Unit = {
      for (playerCard: PlayerCard <- selectedItems){
        playerCard.setSelected(false)
      }

      selectedItems = List.empty
    }
  }

}
