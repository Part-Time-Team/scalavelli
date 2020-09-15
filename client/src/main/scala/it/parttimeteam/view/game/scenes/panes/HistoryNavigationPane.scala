package it.parttimeteam.view.game.scenes.panes

import it.parttimeteam.view.utils.ScalavelliButton
import scalafx.geometry.Pos
import scalafx.scene.layout.HBox

/**
  * Pane which allows user to move between history states
  */
trait HistoryNavigationPane extends HBox with ActionGamePane {

  /**
    * Sets the redo button enabled or not
    *
    * @param enabled if the button should be enabled
    */
  def setRedoEnabled(enabled: Boolean)


  /**
    * Sets the undo button enabled or not
    *
    * @param enabled if the button should be enabled
    */
  def setUndoEnabled(enabled: Boolean)

  /**
    * Sets the reset button enabled or not
    *
    * @param enabled if the button should be enabled
    */
  def setResetEnabled(enabled: Boolean)
}

/**
  * Listener for HistoryNavigationPane callback
  */
trait HistoryNavigationListener {

  /**
    * Action performed when undo button has been clicked
    */
  def onUndoClick(): Unit

  /**
    * Action performed when redo button has been clicked
    */
  def onRedoClick(): Unit

  /**
    * Action performed when reset button has been clicked
    */
  def onResetClick(): Unit
}

object HistoryNavigationPane {

  class HistoryNavigationPaneImpl(listener: HistoryNavigationListener) extends HistoryNavigationPane {
    val undoBtn = ScalavelliButton("", () => listener.onUndoClick(), "images/undo.png", 15d, 30d)
    val redoBtn = ScalavelliButton("", () => listener.onRedoClick(), "images/redo.png", 15d, 30d)
    val resetBtn = ScalavelliButton("Reset", () => listener.onResetClick(), 60d)

    this.alignment = Pos.Center
    this.spacing = 5d
    this.children.addAll(undoBtn, redoBtn, resetBtn)

    /** @inheritdoc*/
    override def disableActions(): Unit = {
      undoBtn.setDisable(true)
      redoBtn.setDisable(true)
      resetBtn.setDisable(true)
    }

    /** @inheritdoc*/
    override def enableActions(): Unit = {
      undoBtn.setDisable(false)
      redoBtn.setDisable(false)
      resetBtn.setDisable(false)
    }

    /** @inheritdoc*/
    override def setRedoEnabled(enabled: Boolean): Unit = {
      redoBtn.setDisable(!enabled)
    }

    /** @inheritdoc*/
    override def setUndoEnabled(enabled: Boolean): Unit = {
      undoBtn.setDisable(!enabled)
    }

    /** @inheritdoc*/
    override def setResetEnabled(enabled: Boolean): Unit = {
      resetBtn.setDisable(!enabled)
    }
  }

}