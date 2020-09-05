package it.parttimeteam.view.game.scenes

import it.parttimeteam.core.cards.Card
import it.parttimeteam.gamestate.PlayerGameState
import it.parttimeteam.view.game.listeners.GameSceneListener
import it.parttimeteam.view.game.scenes.GameScene.BoardListener
import it.parttimeteam.view.game.scenes.model.{GameCard, GameCardCombination}
import it.parttimeteam.view.game.scenes.panes.ActionBar.ActionBarImpl
import it.parttimeteam.view.game.scenes.panes.BottomBar.BottomBarImpl
import it.parttimeteam.view.game.scenes.panes.CenterPane.CenterPaneImpl
import it.parttimeteam.view.game.scenes.panes.InitMatchDialog.InitMatchDialogImpl
import it.parttimeteam.view.game.scenes.panes.RightBar.RightBarImpl
import it.parttimeteam.view.game.scenes.panes.{ActionBar, BottomBar, CenterPane, RightBar}
import it.parttimeteam.view.game.{MachiavelliGameStage, SelectionManager}
import scalafx.application.Platform
import scalafx.scene.layout.{BorderPane, VBox}

/** @inheritdoc */
class GameSceneImpl(val parentStage: MachiavelliGameStage) extends GameScene {
  val handSelectionManager: SelectionManager[GameCard] = SelectionManager()
  val boardSelectionManager: SelectionManager[GameCard] = SelectionManager()
  val combinationSelectionManager: SelectionManager[GameCardCombination] = SelectionManager()
  val initMatchDialog = new InitMatchDialogImpl(parentStage)

  val sceneListener: GameSceneListener = new GameSceneListener {

    /** @inheritdoc */
    override def pickCombination(combinationId: String): Unit = {
      parentStage.pickCombination(combinationId)
    }

    /** @inheritdoc */
    override def makeCombination(): Unit = {
      val cards: Seq[Card] = handSelectionManager.getSelectedItems.map(p => p.getCard)
      parentStage.makeCombination(cards)
    }

    /** @inheritdoc */
    override def pickCards(): Unit = {
      val cards: Seq[Card] = boardSelectionManager.getSelectedItems.map(p => p.getCard)
      parentStage.pickCards(cards)
    }

    /** @inheritdoc */
    override def sortHandBySuit(): Unit = {
      parentStage.sortHandBySuit()
    }

    /** @inheritdoc */
    override def sortHandByRank(): Unit = {
      parentStage.sortHandByRank()
    }

    /** @inheritdoc */
    override def endTurn(): Unit = {
      parentStage.endTurn()
    }

    /** @inheritdoc */
    override def nextState(): Unit = {
      parentStage.nextState()
    }

    /** @inheritdoc */
    override def previousState(): Unit = {
      parentStage.previousState()
    }

    /** @inheritdoc */
    override def resetState(): Unit = {
      parentStage.resetHistory()
    }

    /** @inheritdoc */
    override def clearHandSelection(): Unit = {
      handSelectionManager.clearSelection()
      updateActionBarButtons()
    }

    /** @inheritdoc */
    override def updateCombination(): Unit = {
      val combination = combinationSelectionManager.getSelectedItems.head
      val selectedCards = handSelectionManager.getSelectedItems
      parentStage.updateCardCombination(combination.getCombination.id, selectedCards.map(c => c.getCard))
    }
  }

  val rightBar: RightBar = new RightBarImpl(sceneListener)

  val bottom = new VBox()
  val actionBar: ActionBar = new ActionBarImpl(sceneListener)

  val bottomBar: BottomBar = new BottomBarImpl((card: GameCard) => {
    handSelectionManager.onItemSelected(card)
    updateActionBarButtons()
  })

  bottom.children.addAll(actionBar, bottomBar)

  val centerPane: CenterPane = new CenterPaneImpl(new BoardListener {

    /** @inheritdoc */
    override def onCombinationClicked(cardCombination: GameCardCombination): Unit = {
      combinationSelectionManager.onItemSelected(cardCombination)
      updateActionBarButtons()
    }

    /** @inheritdoc */
    override def onCardClicked(card: GameCard): Unit = {
      boardSelectionManager.onItemSelected(card)
      updateActionBarButtons()
    }

    /** @inheritdoc */
    override def onPickCombinationClick(cardCombination: GameCardCombination): Unit = {
      parentStage.pickCombination(cardCombination.getCombination.id)
    }
  })

  stylesheets.add("/styles/gameStyle.css")

  val borderPane: BorderPane = new BorderPane()

  borderPane.bottom = bottom
  borderPane.right = rightBar
  borderPane.center = centerPane

  root = borderPane

  /** @inheritdoc */
  override def setState(state: PlayerGameState): Unit = {
    Platform.runLater({
      centerPane.setBoard(state.board)

      bottomBar.setHand(state.hand)

      rightBar.setOtherPlayers(state.otherPlayers)
    })
  }

  /** @inheritdoc */
  override def showInitMatch(): Unit = {
    Platform.runLater({
      initMatchDialog.showDialog()
    })
  }

  /** @inheritdoc */
  override def hideInitMatch(): Unit = {
    Platform.runLater({
      initMatchDialog.hideDialog()
    })
  }

  /** @inheritdoc */
  override def setMessage(message: String): Unit = {
    Platform.runLater({
      rightBar.setMessage(message)
    })
  }

  /** @inheritdoc */
  override def showTimer(): Unit = {
    Platform.runLater({
      rightBar.showTimer()
    })
  }

  /** @inheritdoc */
  private def updateActionBarButtons(): Unit = {
    actionBar.enableMakeCombination(!handSelectionManager.isSelectionEmpty)
    actionBar.enableClearHandSelection(!handSelectionManager.isSelectionEmpty)
    actionBar.enableUpdateCardCombination(combinationSelectionManager.getSelectedItems.size == 1 && handSelectionManager.getSelectedItems.size == 1)
    actionBar.enablePickCards(!boardSelectionManager.isSelectionEmpty)
  }

  /** @inheritdoc */
  override def hideTimer(): Unit = {
    rightBar.hideTimer()
  }

  /** @inheritdoc */
  override def disableActions(): Unit = {
    rightBar.disableActions()
    centerPane.disableActions()
    actionBar.disableActions()
    bottomBar.disableActions()
  }

  /** @inheritdoc */
  override def enableActions(): Unit = {
    rightBar.enableActions()
    centerPane.enableActions()
    actionBar.enableActions()
    bottomBar.enableActions()
  }

  /** @inheritdoc */
  override def setHistoryState(canUndo: Boolean, canRedo: Boolean): Unit = {
    rightBar.setUndoEnabled(canUndo)
    rightBar.setRedoEnabled(canRedo)
  }
}
