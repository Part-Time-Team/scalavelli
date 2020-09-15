package it.parttimeteam.view.game.scenes

import it.parttimeteam.core.cards.Card
import it.parttimeteam.model.game.ClientGameState
import it.parttimeteam.view.game.listeners.GameStageListener
import it.parttimeteam.view.game.scenes.GameScene.BoardListener
import it.parttimeteam.view.game.scenes.model.{GameCard, GameCardCombination}
import it.parttimeteam.view.game.scenes.panes.ActionBar.ActionBarImpl
import it.parttimeteam.view.game.scenes.panes.BoardPane.BoardPaneImpl
import it.parttimeteam.view.game.scenes.panes.HandBar.HandBarImpl
import it.parttimeteam.view.game.scenes.panes.InitMatchDialog.InitMatchDialogImpl
import it.parttimeteam.view.game.scenes.panes.SidePane.SidePaneImpl
import it.parttimeteam.view.game.scenes.panes._
import it.parttimeteam.view.game.{GameStage, SelectionManager}
import it.parttimeteam.view.utils.Strings
import scalafx.application.Platform
import scalafx.scene.layout.{BorderPane, VBox}

/** @inheritdoc */
class GameSceneImpl(val parentStage: GameStage, listener: GameStageListener) extends GameScene {
  var inTurn = false

  val handSelectionManager: SelectionManager[GameCard] = SelectionManager(allowOnlyOne = false)
  val boardSelectionManager: SelectionManager[GameCard] = SelectionManager(allowOnlyOne = false)
  val combinationSelectionManager: SelectionManager[GameCardCombination] = SelectionManager(allowOnlyOne = true)
  val initMatchDialog = new InitMatchDialogImpl(parentStage)

  val gameInfoBarListener: GameInfoBarListener = new GameInfoBarListener {
    /** @inheritdoc*/
    override def endTurn(): Unit = listener.endTurn()

    /** @inheritdoc*/
    override def leaveGame(): Unit = listener.leaveGame()

    /** @inheritdoc*/
    override def nextState(): Unit = listener.nextState()

    /** @inheritdoc*/
    override def previousState(): Unit = listener.previousState()

    /** @inheritdoc*/
    override def resetState(): Unit = listener.resetHistory()
  }

  val boardListener: BoardListener = new BoardListener {
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
      listener.pickCombination(cardCombination.getCombination.id)
    }
  }

  var actionBarListener: ActionBarListener = new ActionBarListener {

    /** @inheritdoc*/
    override def pickCombination(combinationId: String): Unit = {
      listener.pickCombination(combinationId)
    }

    /** @inheritdoc */
    override def makeCombination(): Unit = {
      val cards: Seq[Card] = handSelectionManager.getSelectedItems.map(p => p.getCard)
      listener.makeCombination(cards)
    }

    /** @inheritdoc */
    override def pickCards(): Unit = {
      val cards: Seq[Card] = boardSelectionManager.getSelectedItems.map(p => p.getCard)
      listener.pickCards(cards)
    }

    /** @inheritdoc */
    override def sortHandBySuit(): Unit = {
      listener.sortHandBySuit()
    }

    /** @inheritdoc */
    override def sortHandByRank(): Unit = {
      listener.sortHandByRank()
    }

    /** @inheritdoc*/
    override def clearHandSelection(): Unit = {
      handSelectionManager.clearSelection()
      updateActionBarButtons()
    }

    /** @inheritdoc */
    override def updateCombination(): Unit = {
      val combination = combinationSelectionManager.getSelectedItems.head
      val selectedCards = handSelectionManager.getSelectedItems
      listener.updateCardCombination(combination.getCombination.id, selectedCards.map(c => c.getCard))
    }
  }

  val rightBar: SidePane = new SidePaneImpl(gameInfoBarListener)

  val bottom = new VBox()

  val actionBar: ActionBar = new ActionBarImpl(actionBarListener)

  val handBar: HandBar = new HandBarImpl((card: GameCard) => {
    handSelectionManager.onItemSelected(card)
    updateActionBarButtons()
  })

  bottom.children.addAll(actionBar, handBar)
  bottom.getStyleClass.add("woodBack")

  val centerPane: BoardPane = new BoardPaneImpl(boardListener)

  stylesheets.add("/styles/gameStyle.css")

  val borderPane: BorderPane = new BorderPane()

  borderPane.bottom = bottom
  borderPane.right = rightBar
  borderPane.center = centerPane

  root = borderPane

  /** @inheritdoc */
  override def setState(clientGameState: ClientGameState): Unit = {
    this.handSelectionManager.clearSelection()
    this.boardSelectionManager.clearSelection()
    this.combinationSelectionManager.clearSelection()
    Platform.runLater({
      centerPane.setBoard(clientGameState.playerGameState.board)
      handBar.setHand(clientGameState.playerGameState.hand)
      rightBar.setOtherPlayers(clientGameState.playerGameState.otherPlayers)
      rightBar.setUndoEnabled(inTurn && clientGameState.canUndo)
      rightBar.setRedoEnabled(inTurn && clientGameState.canRedo)
      rightBar.setResetEnabled(inTurn && clientGameState.canReset)
      rightBar.setNextEnabled(inTurn)
      rightBar.setNextText(if (clientGameState.canDrawCard) Strings.PASS_AND_DRAW_BTN else Strings.PASS_BTN)
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

  /** @inheritdoc*/
  override def setInTurn(inTurn: Boolean): Unit = {
    this.inTurn = inTurn
    updateActionBarButtons()
    rightBar.setNextEnabled(inTurn)
  }

  /** @inheritdoc */
  private def updateActionBarButtons(): Unit = {
    actionBar.enableMakeCombination(!handSelectionManager.isSelectionEmpty && inTurn)
    actionBar.enableClearHandSelection(!handSelectionManager.isSelectionEmpty)
    actionBar.enableUpdateCardCombination(combinationSelectionManager.getSelectedItems.size == 1 && handSelectionManager.getSelectedItems.size == 1 && inTurn)
    actionBar.enablePickCards(!boardSelectionManager.isSelectionEmpty && inTurn)
  }

  /** @inheritdoc*/
  override def disableActions(): Unit = {
    rightBar.disableActions()
    centerPane.disableActions()
    actionBar.disableActions()
    handBar.disableActions()
  }

  /** @inheritdoc */
  override def enableActions(): Unit = {
    rightBar.enableActions()
    centerPane.enableActions()
    actionBar.enableActions()
    handBar.enableActions()
  }

  /** @inheritdoc*/
  override def showTimer(minutes: Long, seconds: Long): Unit = rightBar.showTimer(minutes, seconds)

  /** @inheritdoc*/
  override def updateTimer(minutes: Long, seconds: Long): Unit = rightBar.updateTimer(minutes, seconds)

  /** @inheritdoc*/
  override def notifyTimerEnd(): Unit = rightBar.notifyTimerEnd()

  /** @inheritdoc*/
  override def hideTimer(): Unit = rightBar.hideTimer()
}
