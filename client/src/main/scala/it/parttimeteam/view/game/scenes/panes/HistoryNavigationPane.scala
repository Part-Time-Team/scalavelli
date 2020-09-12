package it.parttimeteam.view.game.scenes.panes

import it.parttimeteam.view.utils.ScalavelliButton
import scalafx.geometry.Pos
import scalafx.scene.layout.HBox

trait HistoryNavigationPane extends HBox with ActionGamePane {

  def setRedoEnabled(enabled: Boolean)

  def setUndoEnabled(enabled: Boolean)

  def setResetEnabled(enabled: Boolean)
}

trait HistoryNavigationListener {

  def onUndoClick(): Unit

  def onRedoClick(): Unit

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

    /** @inheritdoc */
    override def disableActions(): Unit = {
      undoBtn.setDisable(true)
      redoBtn.setDisable(true)
      resetBtn.setDisable(true)
    }

    /** @inheritdoc */
    override def enableActions(): Unit = {
      undoBtn.setDisable(false)
      redoBtn.setDisable(false)
      resetBtn.setDisable(false)
    }

    /** @inheritdoc */
    override def setRedoEnabled(enabled: Boolean): Unit = {
      redoBtn.setDisable(!enabled)
    }

    /** @inheritdoc */
    override def setUndoEnabled(enabled: Boolean): Unit = {
      undoBtn.setDisable(!enabled)
    }

    /** @inheritdoc */
    override def setResetEnabled(enabled: Boolean): Unit = {
      resetBtn.setDisable(!enabled)
    }
  }

}