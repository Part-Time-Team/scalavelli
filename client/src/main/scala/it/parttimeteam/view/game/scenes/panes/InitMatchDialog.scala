package it.parttimeteam.view.game.scenes.panes

import it.parttimeteam.view.ViewConfig
import it.parttimeteam.view.utils.Strings
import scalafx.geometry.Pos
import scalafx.scene.Scene
import scalafx.scene.control.{Label, ProgressBar}
import scalafx.scene.layout.VBox
import scalafx.stage.{Modality, Stage, StageStyle}

/**
  * Blocking dialog which can be displayed while setting up the game view.
  */
trait InitMatchDialog extends Stage {

  /**
    * Allows to display the dialog.
    */
  def showDialog(): Unit

  /**
    * Allows to hide the dialog.
    */
  def hideDialog(): Unit
}

object InitMatchDialog {

  class InitMatchDialogImpl(parentStage: Stage) extends InitMatchDialog {
    val progressBar: ProgressBar = new ProgressBar()
    this.initStyle(StageStyle.Decorated)
    this.setResizable(false)
    this.initModality(Modality.WindowModal)
    this.setTitle(Strings.GAME_LOADING_TITLE)
    this.setMinWidth(ViewConfig.INIT_MATCH_DIALOG_MIN_WIDTH)
    this.setMinHeight(ViewConfig.INIT_MATCH_DIALOG_MIN_HEIGHT)

    val label = new Label(Strings.GAME_LOADING_MESSAGE)

    val vb = new VBox()
    vb.setSpacing(ViewConfig.DEFAULT_SPACING)
    vb.setAlignment(Pos.Center)
    vb.getChildren.addAll(label, progressBar)
    val dialogScene = new Scene(vb)
    this.setScene(dialogScene)
    this.initOwner(parentStage)

    /** @inheritdoc*/
    override def showDialog(): Unit = {
      this.showAndWait()
      this.setAlwaysOnTop(true)
    }

    /** @inheritdoc*/
    override def hideDialog(): Unit = {
      this.close()
    }
  }

}

