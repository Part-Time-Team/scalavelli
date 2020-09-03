package it.parttimeteam.view.game.scenes.panes

import it.parttimeteam.core.cards.Card
import it.parttimeteam.core.collections.{Board, CardCombination}
import it.parttimeteam.view.ViewConfig
import it.parttimeteam.view.game.scenes.GameScene.BoardListener
import it.parttimeteam.view.game.scenes.model.PlayerCombination
import it.parttimeteam.view.game.scenes.model.PlayerCombination.PlayerCombinationImpl
import scalafx.geometry.Insets
import scalafx.scene.control.ScrollPane
import scalafx.scene.layout.VBox

trait CenterPane extends ScrollPane {
  def setBoard(board: Board): Unit
}

object CenterPane {

  class CenterPaneImpl(boardListener: BoardListener) extends CenterPane {
    val tableCombinations = new VBox()
    tableCombinations.spacing = 10d
    tableCombinations.padding = Insets(ViewConfig.CARD_Y_TRANSLATION, ViewConfig.screenPadding, ViewConfig.screenPadding, ViewConfig.screenPadding)

    var selectedCards: Seq[Card] = Seq()

    this.setContent(tableCombinations)

    override def setBoard(board: Board): Unit = {
      for (combination: CardCombination <- board.combinations) {
        val playerCombination: PlayerCombination = new PlayerCombinationImpl(combination, boardListener)
        tableCombinations.children.add(playerCombination)
      }
    }
  }

}

