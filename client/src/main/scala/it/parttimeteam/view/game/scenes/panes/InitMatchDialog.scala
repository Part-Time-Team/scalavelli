package it.parttimeteam.view.game.scenes.panes

import scalafx.geometry.Pos
import scalafx.scene.Scene
import scalafx.scene.control.{Label, ProgressBar}
import scalafx.scene.layout.VBox
import scalafx.stage.{Modality, Stage, StageStyle}

trait InitMatchDialog extends Stage {
  def showDialog(): Unit

  def hideDialog(): Unit
}

object InitMatchDialog {
  class InitMatchDialogImpl(parentStage: Stage) extends InitMatchDialog {
    val progressBar: ProgressBar = new ProgressBar()
    this.initStyle(StageStyle.Decorated)
    this.setResizable(false)
    this.initModality(Modality.WindowModal)
    this.setTitle("Game loading")
    this.setMinWidth(200)
    this.setMinHeight(100)

    val label = new Label("Preparing your cards...")

    val vb = new VBox()
    vb.setSpacing(5)
    vb.setAlignment(Pos.Center)
    vb.getChildren.addAll(label, progressBar)
    val dialogScene = new Scene(vb)
    this.setScene(dialogScene)
    this.initOwner(parentStage)

    override def showDialog(): Unit = {
      this.showAndWait()
      this.setAlwaysOnTop(true)
    }

    override def hideDialog(): Unit = {
      this.close()
    }
  }
}

