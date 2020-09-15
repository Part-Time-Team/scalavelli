package it.parttimeteam.view.game.scenes.panes

import it.parttimeteam.core.collections.{Board, CardCombination}
import it.parttimeteam.view.ViewConfig
import it.parttimeteam.view.game.scenes.GameScene.BoardListener
import it.parttimeteam.view.game.scenes.model.GameCardCombination
import it.parttimeteam.view.game.scenes.model.GameCardCombination.GameCardCombinationImpl
import scalafx.geometry.Insets
import scalafx.scene.control.ScrollPane
import scalafx.scene.layout.TilePane

/**
  * Pane which contains the game board.
  */
trait BoardPane extends ScrollPane with ActionGamePane {

  /**
    * Sets the game board inside a ScrollPane.
    *
    * @param board the actual game Board
    */
  def setBoard(board: Board): Unit
}

object BoardPane {

  class BoardPaneImpl(boardListener: BoardListener) extends BoardPane {

    var tilePane: TilePane = new TilePane()
    tilePane.prefColumns = 2
    tilePane.prefWidth <= this.getWidth
    tilePane.vgap = ViewConfig.BOARD_TILE_GAP
    tilePane.hgap = ViewConfig.BOARD_TILE_GAP

    tilePane.padding = Insets(ViewConfig.CARD_Y_TRANSLATION, ViewConfig.SCREEN_PADDING, ViewConfig.SCREEN_PADDING, ViewConfig.SCREEN_PADDING)
    content = tilePane
    this.setFitToWidth(true)

    this.getStyleClass.add("greenBack")


    /** @inheritdoc */
    override def setBoard(board: Board): Unit = {
      tilePane.children.clear()

      for (combination: CardCombination <- board.combinations) {
        val playerCombination: GameCardCombination = new GameCardCombinationImpl(combination, boardListener)
        tilePane.children.add(playerCombination)
      }
    }

    /** @inheritdoc */
    override def disableActions(): Unit = {
      tilePane.children.forEach(playerCombination => {
        playerCombination.asInstanceOf[GameCardCombination].disableActions()
      })
    }

    /** @inheritdoc */
    override def enableActions(): Unit = {
      tilePane.children.forEach(playerCombination => {
        playerCombination.asInstanceOf[GameCardCombination].enableActions()
      })
    }
  }

}

