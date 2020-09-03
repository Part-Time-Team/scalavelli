package it.parttimeteam.view.game.scenes

import it.parttimeteam.core.cards.Card
import it.parttimeteam.gamestate.PlayerGameState
import it.parttimeteam.view.game.listeners.GameSceneToStageListener
import it.parttimeteam.view.game.scenes.panes.{BottomBar, CenterPane, RightBar}
import it.parttimeteam.view.game.{MachiavelliGamePrimaryStage, PlayerCard, SelectionManager}
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

trait BoardListener {
  def onBoardCardClicked(card: PlayerCard)
}

trait HandListener {
  def onHandCardClicked(card: PlayerCard)
}

class GameSceneImpl(val parentStage: MachiavelliGamePrimaryStage) extends GameScene {
  val handSelectionManager: SelectionManager = SelectionManager()
  val boardSelectionManager: SelectionManager = SelectionManager()

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

  val rightBar: RightBar = new RightBar(sceneListener)

  val bottomBar: BottomBar = new BottomBar(sceneListener, (card: PlayerCard) => {
    handSelectionManager.onItemSelected(card)
    bottomBar.setMakeCombinationEnabled(!handSelectionManager.isSelectionEmpty)
    bottomBar.setClearSelectionEnabled(!handSelectionManager.isSelectionEmpty)
  })

  val centerPane: CenterPane = new CenterPane(sceneListener, (card: PlayerCard) => {
    boardSelectionManager.onItemSelected(card)
    bottomBar.setPickCardsEnabled(!boardSelectionManager.isSelectionEmpty)
  })


  stylesheets.add("/styles/gameStyle.css")

  var initMatchDialog: Stage = _

  val borderPane: BorderPane = new BorderPane()

  borderPane.bottom = bottomBar
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
      initMatchDialog = new Stage()
      val progressBar: ProgressBar = new ProgressBar()
      initMatchDialog.initStyle(StageStyle.Decorated)
      initMatchDialog.setResizable(false)
      initMatchDialog.initModality(Modality.WindowModal)
      initMatchDialog.setTitle("Game loading")
      initMatchDialog.setMinWidth(200)
      initMatchDialog.setMinHeight(100)

      val label = new Label("Preparing your cards...")

      val vb = new VBox()
      vb.setSpacing(5)
      vb.setAlignment(Pos.Center)
      vb.getChildren.addAll(label, progressBar)
      val scene = new Scene(vb)
      initMatchDialog.setScene(scene)
      initMatchDialog.initOwner(parentStage)
      initMatchDialog.showAndWait()
      initMatchDialog.setAlwaysOnTop(true)
    })

  }

  override def hideInitMatch(): Unit = {
    Platform.runLater({
      initMatchDialog.hide()
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