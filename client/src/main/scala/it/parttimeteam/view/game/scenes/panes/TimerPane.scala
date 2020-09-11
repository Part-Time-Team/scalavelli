package it.parttimeteam.view.game.scenes.panes

import it.parttimeteam.view.utils.MachiavelliLabel
import scalafx.scene.layout.VBox

trait TimerPane extends VBox {

  def displayMessage(message: String): Unit

  def set(minutes: Long, seconds: Long): Unit

  def show(startingMinutes: Long, startingSeconds: Long): Unit

  def hide(): Unit

  def setTime(time: String): Unit
}

object TimerPane {

  class TimerPaneImpl extends TimerPane {
    val timerLabel = MachiavelliLabel("Timer")
    timerLabel.getStyleClass.add("boldText")

    val countdownLabel = MachiavelliLabel()

    this.children.addAll(timerLabel, countdownLabel)

    override def hide(): Unit = {
      visible = false
    }


    override def setTime(time: String): Unit = {
      countdownLabel.setText(time)
    }

    override def displayMessage(message: String): Unit = {
      setText(message)
    }

    override def set(minutes: Long, seconds: Long): Unit = setText(timeToLabel(minutes, seconds))

    override def show(startingMinutes: Long, startingSeconds: Long): Unit = {
      visible = true
      setText(timeToLabel(startingMinutes, startingSeconds))
    }

    private def setText(message: String): Unit = countdownLabel.text = message

    private def timeToLabel(minutes: Long, seconds: Long): String = s"$minutes:$seconds"

  }

}


