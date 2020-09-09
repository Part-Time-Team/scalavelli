package it.parttimeteam.view.game.scenes

import it.parttimeteam.core.cards.Card
import it.parttimeteam.model.game.ClientGameState
import it.parttimeteam.view.game.listeners.GameSceneListener
import it.parttimeteam.view.game.scenes.GameScene.BoardListener
import it.parttimeteam.view.game.scenes.model.{GameCard, GameCardCombination}
import it.parttimeteam.view.game.scenes.panes.ActionBar.ActionBarImpl
import it.parttimeteam.view.game.scenes.panes.BoardPane.BoardPaneImpl
import it.parttimeteam.view.game.scenes.panes.GameInfoBar.GameInfoBarImpl
import it.parttimeteam.view.game.scenes.panes.HandBar.HandBarImpl
import it.parttimeteam.view.game.scenes.panes.InitMatchDialog.InitMatchDialogImpl
import it.parttimeteam.view.game.scenes.panes.{ActionBar, BoardPane, GameInfoBar, HandBar}
import it.parttimeteam.view.game.{MachiavelliGameStage, SelectionManager}
import scalafx.application.Platform
import scalafx.scene.layout.{BorderPane, VBox}

/** @inheritdoc*/
class GameSceneImpl(val parentStage: MachiavelliGameStage) extends GameScene {
  var inTurn = false

  val handSelectionManager: SelectionManager[GameCard] = SelectionManager()
  val boardSelectionManager: SelectionManager[GameCard] = SelectionManager()
  val combinationSelectionManager: SelectionManager[GameCardCombination] = SelectionManager()
  val initMatchDialog = new InitMatchDialogImpl(parentStage)

  val sceneListener: GameSceneListener = new GameSceneListener {

    /** @inheritdoc*/
    override def pickCombination(combinationId: String): Unit = {
      parentStage.pickCombination(combinationId)
    }

    /** @inheritdoc*/
    override def makeCombination(): Unit = {
      val cards: Seq[Card] = handSelectionManager.getSelectedItems.map(p => p.getCard)
      parentStage.makeCombination(cards)
    }

    /** @inheritdoc*/
    override def pickCards(): Unit = {
      val cards: Seq[Card] = boardSelectionManager.getSelectedItems.map(p => p.getCard)
      parentStage.pickCards(cards)
    }

    /** @inheritdoc*/
    override def sortHandBySuit(): Unit = {
      parentStage.sortHandBySuit()
    }

    /** @inheritdoc*/
    override def sortHandByRank(): Unit = {
      parentStage.sortHandByRank()
    }

    /** @inheritdoc*/
    override def endTurn(): Unit = {
      parentStage.endTurn()
    }

    /** @inheritdoc*/
    override def nextState(): Unit = {
      parentStage.nextState()
    }

    /** @inheritdoc*/
    override def previousState(): Unit = {
      parentStage.previousState()
    }

    /** @inheritdoc*/
    override def resetState(): Unit = {
      parentStage.resetHistory()
    }

    /** @inheritdoc*/
    override def clearHandSelection(): Unit = {
      handSelectionManager.clearSelection()
      updateActionBarButtons()
    }

    /** @inheritdoc*/
    override def updateCombination(): Unit = {
      val combination = combinationSelectionManager.getSelectedItems.head
      val selectedCards = handSelectionManager.getSelectedItems
      parentStage.updateCardCombination(combination.getCombination.id, selectedCards.map(c => c.getCard))
    }

    /**
      * The player leaves the game.
      */
    override def leaveGame(): Unit = {
      parentStage.leaveGame()
    }
  }

  val rightBar: GameInfoBar = new GameInfoBarImpl(sceneListener)

  val bottom = new VBox()
  val actionBar: ActionBar = new ActionBarImpl(sceneListener)

  val bottomBar: HandBar = new HandBarImpl((card: GameCard) => {
    handSelectionManager.onItemSelected(card)
    updateActionBarButtons()
  })

  bottom.children.addAll(actionBar, bottomBar)

  val centerPane: BoardPane = new BoardPaneImpl(new BoardListener {

    /** @inheritdoc*/
    override def onCombinationClicked(cardCombination: GameCardCombination): Unit = {
      combinationSelectionManager.onItemSelected(cardCombination)
      updateActionBarButtons()
    }

    /** @inheritdoc*/
    override def onCardClicked(card: GameCard): Unit = {
      boardSelectionManager.onItemSelected(card)
      updateActionBarButtons()
    }

    /** @inheritdoc*/
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

  /** @inheritdoc*/
  override def setState(clientGameState: ClientGameState): Unit = {
    this.handSelectionManager.clearSelection()
    this.boardSelectionManager.clearSelection()
    this.combinationSelectionManager.clearSelection()
    Platform.runLater({
      centerPane.setBoard(clientGameState.playerGameState.board)
      bottomBar.setHand(clientGameState.playerGameState.hand)
      rightBar.setOtherPlayers(clientGameState.playerGameState.otherPlayers)
      rightBar.setUndoEnabled(inTurn && clientGameState.canUndo)
      rightBar.setRedoEnabled(inTurn && clientGameState.canRedo)
      rightBar.setResetEnabled(inTurn && clientGameState.canReset)
      rightBar.setNextEnabled(inTurn)
      rightBar.setNextText(if (clientGameState.canDrawCard) "Pass & Draw" else "Pass")
    })
  }

  /** @inheritdoc*/
  override def showInitMatch(): Unit = {
    Platform.runLater({
      initMatchDialog.showDialog()
    })
  }

  /** @inheritdoc*/
  override def hideInitMatch(): Unit = {
    Platform.runLater({
      initMatchDialog.hideDialog()
    })
  }

  /** @inheritdoc*/
  override def setMessage(message: String): Unit = {
    Platform.runLater({
      rightBar.setMessage(message)
    })
  }

  /** @inheritdoc*/
  override def showTimer(): Unit = {
    Platform.runLater({
      rightBar.showTimer()
    })
  }

  /** @inheritdoc*/
  override def setInTurn(inTurn: Boolean): Unit = {
    this.inTurn = inTurn
    updateActionBarButtons()
    rightBar.setNextEnabled(inTurn)
  }

  /** @inheritdoc*/
  private def updateActionBarButtons(): Unit = {
    actionBar.enableMakeCombination(!handSelectionManager.isSelectionEmpty && inTurn)
    actionBar.enableClearHandSelection(!handSelectionManager.isSelectionEmpty)
    actionBar.enableUpdateCardCombination(combinationSelectionManager.getSelectedItems.size == 1 && handSelectionManager.getSelectedItems.size == 1 && inTurn)
    actionBar.enablePickCards(!boardSelectionManager.isSelectionEmpty && inTurn)
  }

  /** @inheritdoc*/
  override def hideTimer(): Unit = {
    rightBar.hideTimer()
  }

  /** @inheritdoc*/
  override def disableActions(): Unit = {
    rightBar.disableActions()
    centerPane.disableActions()
    actionBar.disableActions()
    bottomBar.disableActions()
  }

  /** @inheritdoc*/
  override def enableActions(): Unit = {
    rightBar.enableActions()
    centerPane.enableActions()
    actionBar.enableActions()
    bottomBar.enableActions()
  }
}
