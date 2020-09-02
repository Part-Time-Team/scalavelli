package it.parttimeteam.view.game.scenes

import it.parttimeteam.gamestate.PlayerGameState
import it.parttimeteam.view.game.listeners.GameSceneListener
import it.parttimeteam.view.game.scenes.panes.{BottomBar, CenterPane, RightBar}
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

class GameSceneImpl(val parentStage: Stage, val listener: GameSceneListener) extends GameScene {
  val rightBar: RightBar = new RightBar(listener)
  val bottomBar: BottomBar = new BottomBar(listener)
  val centerPane: CenterPane = new CenterPane(listener)

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