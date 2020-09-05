package it.parttimeteam.view.game.scenes.panes

import it.parttimeteam.core.cards.Card
import it.parttimeteam.core.collections.{Board, CardCombination}
import it.parttimeteam.view.ViewConfig
import it.parttimeteam.view.game.scenes.GameScene.BoardListener
import it.parttimeteam.view.game.scenes.model.GameCardCombination
import it.parttimeteam.view.game.scenes.model.GameCardCombination.GameCardCombinationImpl
import scalafx.geometry.Insets
import scalafx.scene.control.ScrollPane
import scalafx.scene.layout.VBox

/**
  * Pane which contains the game board.
  */
trait CenterPane extends ScrollPane with BaseGamePane {

  /**
    * Sets the game board inside a ScrollPane.
    *
    * @param board the actual game Board
    */
  def setBoard(board: Board): Unit
}

object CenterPane {

  class CenterPaneImpl(boardListener: BoardListener) extends CenterPane {
    val tableCombinations = new VBox()
    tableCombinations.spacing = 10d
    tableCombinations.padding = Insets(ViewConfig.CARD_Y_TRANSLATION, ViewConfig.screenPadding, ViewConfig.screenPadding, ViewConfig.screenPadding)

    var selectedCards: Seq[Card] = Seq()

    this.setContent(tableCombinations)

    /** @inheritdoc*/
    override def setBoard(board: Board): Unit = {
      for (combination: CardCombination <- board.combinations) {
        val playerCombination: GameCardCombination = new GameCardCombinationImpl(combination, boardListener)
        tableCombinations.children.add(playerCombination)
      }
    }

    /** @inheritdoc*/
    override def disableActions(): Unit = {
      tableCombinations.children.forEach(playerCombination => {
        playerCombination.asInstanceOf[GameCardCombination].disableActions()
      })
    }

    /** @inheritdoc*/
    override def enableActions(): Unit = {
      tableCombinations.children.forEach(playerCombination => {
        playerCombination.asInstanceOf[GameCardCombination].enableActions()
      })
    }
  }

}

