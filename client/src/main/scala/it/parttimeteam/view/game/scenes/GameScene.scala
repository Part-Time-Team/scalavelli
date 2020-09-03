package it.parttimeteam.view.game.scenes

import it.parttimeteam.core.cards.Card
import it.parttimeteam.gamestate.PlayerGameState
import it.parttimeteam.view.game.listeners.GameSceneToStageListener
import it.parttimeteam.view.game.scenes.model.{PlayerCard, PlayerCombination}
import it.parttimeteam.view.game.scenes.panes.ActionBar.ActionBarImpl
import it.parttimeteam.view.game.scenes.panes.BottomBar.BottomBarImpl
import it.parttimeteam.view.game.scenes.panes.CenterPane.CenterPaneImpl
import it.parttimeteam.view.game.scenes.panes.InitMatchDialog.InitMatchDialogImpl
import it.parttimeteam.view.game.scenes.panes.RightBar.RightBarImpl
import it.parttimeteam.view.game.scenes.panes.{ActionBar, BottomBar, CenterPane, RightBar}
import it.parttimeteam.view.game.{MachiavelliGamePrimaryStage, SelectionManager}
import scalafx.application.Platform
import scalafx.scene.Scene
import scalafx.scene.layout.{BorderPane, VBox}

trait GameScene extends Scene {

  def hideInitMatch(): Unit

  def showInitMatch(): Unit

  def setState(state: PlayerGameState): Unit

  def setMessage(message: String): Unit

  def showTimer(): Unit
}

object GameScene {

  trait BoardListener {
    def onPickCombinationClick(cardCombination: PlayerCombination): Unit

    def onCombinationClicked(cardCombination: PlayerCombination): Unit

    def onBoardCardClicked(card: PlayerCard)
  }

  trait HandListener {
    def onHandCardClicked(card: PlayerCard)
  }

  class GameSceneImpl(val parentStage: MachiavelliGamePrimaryStage) extends GameScene {
    val handSelectionManager: SelectionManager[PlayerCard] = SelectionManager()
    val boardSelectionManager: SelectionManager[PlayerCard] = SelectionManager()
    val combinationSelectionManager: SelectionManager[PlayerCombination] = SelectionManager()
    val initMatchDialog = new InitMatchDialogImpl(parentStage)

    val sceneListener: GameSceneToStageListener = new GameSceneToStageListener {
      override def pickCombination(combinationId: String): Unit = {
        parentStage.pickCombination(combinationId)
      }

      override def makeCombination(): Unit = {
        val cards: Seq[Card] = handSelectionManager.getSelectedItems.map(p => p.getCard)
        parentStage.makeCombination(cards)
      }

      override def pickCards(): Unit = {
        val cards: Seq[Card] = boardSelectionManager.getSelectedItems.map(p => p.getCard)
        parentStage.pickCards(cards)
      }

      override def sortHandBySuit(): Unit = {
        parentStage.sortHandBySuit()
      }

      override def sortHandByRank(): Unit = {
        parentStage.sortHandByRank()
      }

      override def endTurn(): Unit = {
        parentStage.endTurn()
      }

      override def nextState(): Unit = {
        parentStage.nextState()
      }

      override def previousState(): Unit = {
        parentStage.previousState()
      }

      override def clearHandSelection(): Unit = {
        handSelectionManager.clearSelection()
        updateActionBarButtons()
      }

      override def updateCombination(): Unit = {
        val combination = combinationSelectionManager.getSelectedItems.head
        val selectedCard = handSelectionManager.getSelectedItems.head
        parentStage.updateCardCombination(combination.getCombination.id, selectedCard.getCard)
      }
    }

    val rightBar: RightBar = new RightBarImpl(sceneListener)

    val bottom = new VBox()
    val actionBar: ActionBar = new ActionBarImpl(sceneListener)

    val bottomBar: BottomBar = new BottomBarImpl((card: PlayerCard) => {
      handSelectionManager.onItemSelected(card)
      updateActionBarButtons()
    })

    bottom.children.addAll(actionBar, bottomBar)

    val centerPane: CenterPane = new CenterPaneImpl(new BoardListener {
      override def onCombinationClicked(cardCombination: PlayerCombination): Unit = {
        combinationSelectionManager.onItemSelected(cardCombination)
        updateActionBarButtons()
      }

      override def onBoardCardClicked(card: PlayerCard): Unit = {
        boardSelectionManager.onItemSelected(card)
        updateActionBarButtons()
      }

      override def onPickCombinationClick(cardCombination: PlayerCombination): Unit = {
        parentStage.pickCombination(cardCombination.getCombination.id)
      }
    })

    stylesheets.add("/styles/gameStyle.css")

    val borderPane: BorderPane = new BorderPane()

    borderPane.bottom = bottom
    borderPane.right = rightBar
    borderPane.center = centerPane

    root = borderPane

    override def setState(state: PlayerGameState): Unit = {
      Platform.runLater({
        centerPane.setBoard(state.board)

        bottomBar.setHand(state.hand)

        rightBar.setOtherPlayers(state.otherPlayers)
      })
    }

    override def showInitMatch(): Unit = {
      Platform.runLater({
        initMatchDialog.showDialog()
      })
    }

    override def hideInitMatch(): Unit = {
      Platform.runLater({
        initMatchDialog.hideDialog()
      })
    }

    override def setMessage(message: String): Unit = {
      Platform.runLater({
        rightBar.setMessage(message)
      })
    }

    override def showTimer(): Unit = {
      Platform.runLater({
        rightBar.showTimer()
      })
    }

    private def updateActionBarButtons(): Unit = {
      actionBar.enableMakeCombination(!handSelectionManager.isSelectionEmpty)
      actionBar.enableClearHandSelection(!handSelectionManager.isSelectionEmpty)
      actionBar.enableUpdateCardCombination(combinationSelectionManager.getSelectedItems.size == 1 && handSelectionManager.getSelectedItems.size == 1)
      actionBar.enablePickCards(!boardSelectionManager.isSelectionEmpty)
    }
  }
}

