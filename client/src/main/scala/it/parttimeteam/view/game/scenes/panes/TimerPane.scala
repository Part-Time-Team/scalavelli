package it.parttimeteam.view.game.scenes.panes

import it.parttimeteam.view.utils.MachiavelliLabel
import scalafx.scene.layout.VBox

trait TimerPane extends VBox {
  def hide(): Unit

  def show(): Unit

  def setTime(time: String): Unit
}

object TimerPane {

  class TimerPaneImpl extends TimerPane {
    val timerLabel = MachiavelliLabel("Timer")
    val countdownLabel = MachiavelliLabel("03:00")

    override def hide(): Unit = {
      visible = false
    }

    override def show(): Unit = {
      visible = true
    }

    override def setTime(time: String): Unit = {
      countdownLabel.setText(time)
    }
  }
}


