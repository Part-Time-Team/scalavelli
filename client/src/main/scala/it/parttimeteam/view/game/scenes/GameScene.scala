package it.parttimeteam.view.game.scenes

import it.parttimeteam.core.cards.Card
import it.parttimeteam.gamestate.PlayerGameState
import it.parttimeteam.view.game.listeners.GameSceneToStageListener
import it.parttimeteam.view.game.scenes.model.PlayerCard
import it.parttimeteam.view.game.scenes.panes.ActionBar.ActionBarImpl
import it.parttimeteam.view.game.scenes.panes.BottomBar.BottomBarImpl
import it.parttimeteam.view.game.scenes.panes.CenterPane.CenterPaneImpl
import it.parttimeteam.view.game.scenes.panes.InitMatchDialog.InitMatchDialogImpl
import it.parttimeteam.view.game.scenes.panes.RightBar.RightBarImpl
import it.parttimeteam.view.game.scenes.panes.{ActionBar, BottomBar, CenterPane, RightBar}
import it.parttimeteam.view.game.{MachiavelliGamePrimaryStage, SelectionManager}
import scalafx.application.Platform
import scalafx.geometry.Pos
import scalafx.scene.Scene
import scalafx.scene.control._
import scalafx.scene.layout.{BorderPane, VBox}
import scalafx.stage.{Modality, Stage, StageStyle}

/**
  * Allow to participate to create a private game by inserting the number of players.
  * Obtains a private code to share to invite other players.
  **/
trait GameScene extends Scene {

  def hideInitMatch(): Unit

  def showInitMatch(): Unit

  def setState(state: PlayerGameState): Unit

  def setMessage(message: String): Unit

  def showTimer(): Unit
}

object GameScene {

  trait BoardListener {
    def onBoardCardClicked(card: PlayerCard)
  }

  trait HandListener {
    def onHandCardClicked(card: PlayerCard)
  }

  class GameSceneImpl(val parentStage: MachiavelliGamePrimaryStage) extends GameScene {
    val handSelectionManager: SelectionManager = SelectionManager()
    val boardSelectionManager: SelectionManager = SelectionManager()
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
      }
    }

    val rightBar: RightBar = new RightBarImpl(sceneListener)

    val bottom = new VBox()
    val actionBar: ActionBar = new ActionBarImpl(sceneListener)

    val bottomBar: BottomBar = new BottomBarImpl(sceneListener, (card: PlayerCard) => {
      handSelectionManager.onItemSelected(card)
      actionBar.enableMakeCombination(!handSelectionManager.isSelectionEmpty)
      actionBar.enableClearHandSelection(!handSelectionManager.isSelectionEmpty)
    })

    bottom.children.addAll(actionBar, bottomBar)

    val centerPane: CenterPane = new CenterPaneImpl(sceneListener, (card: PlayerCard) => {
      boardSelectionManager.onItemSelected(card)
      actionBar.enablePickCards(!boardSelectionManager.isSelectionEmpty)
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
  }

}

