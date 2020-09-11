package it.parttimeteam.view.game

import it.parttimeteam.view.game.scenes.model.SelectableItem
import org.scalatest.wordspec.AnyWordSpecLike

class SelectionManagerSpec extends AnyWordSpecLike {

  val foo: SelectableItem = (_: Boolean) => {}
  val bar: SelectableItem = (_: Boolean) => {}

  "a selection manager" should {

    "be empty when initialized" in {
      val selectionManager: SelectionManager[SelectableItem] = SelectionManager(false)

      assertResult(true)(selectionManager.isSelectionEmpty)
    }

    "contain an element when an item is selected" in {
      val selectionManager: SelectionManager[SelectableItem] = SelectionManager(false)
      selectionManager.onItemSelected(bar)

      assertResult(1)(selectionManager.getSelectedItems.size)
    }

    "be empty when an item is selected twice" in {
      val selectionManager: SelectionManager[SelectableItem] = SelectionManager(false)
      selectionManager.onItemSelected(bar)
      selectionManager.onItemSelected(bar)

      assertResult(0)(selectionManager.getSelectedItems.size)
    }

    "be empty when his size is 0" in {
      val selectionManager: SelectionManager[SelectableItem] = SelectionManager(false)

      assertResult(0)(selectionManager.getSelectedItems.size)
      assertResult(true)(selectionManager.isSelectionEmpty)
    }

    "be empty after the clear function clearSelection" in {
      val selectionManager: SelectionManager[SelectableItem] = SelectionManager(false)
      selectionManager.onItemSelected(bar)
      selectionManager.clearSelection()

      assertResult(true)(selectionManager.isSelectionEmpty)
    }

    "contain two different element when both selected" in {
      val selectionManager: SelectionManager[SelectableItem] = SelectionManager(false)
      selectionManager.onItemSelected(bar)
      selectionManager.onItemSelected(foo)

      assertResult(2)(selectionManager.getSelectedItems.size)
    }

    "contain only one element if allowOnlyOne is enabled" in {
      val selectionManager: SelectionManager[SelectableItem] = SelectionManager(true)
      selectionManager.onItemSelected(bar)
      selectionManager.onItemSelected(foo)

      assertResult(1)(selectionManager.getSelectedItems.size)
    }
  }
}
