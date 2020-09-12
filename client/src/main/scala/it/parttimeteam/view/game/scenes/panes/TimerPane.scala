package it.parttimeteam.view.game.scenes.panes

import it.parttimeteam.view.utils.ScalavelliLabel
import scalafx.scene.layout.VBox

trait TimerPane extends VBox {

  def displayMessage(message: String): Unit

  def set(minutes: Long, seconds: Long): Unit

  def show(startingMinutes: Long, startingSeconds: Long): Unit

  def hide(): Unit
}

object TimerPane {

  class TimerPaneImpl extends TimerPane {
    val timerLabel = ScalavelliLabel("Timer")
    timerLabel.getStyleClass.add("boldText")

    val countdownLabel = ScalavelliLabel()

    this.children.addAll(timerLabel, countdownLabel)

    override def hide(): Unit = {
      visible = false
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

    private def timeToLabel(minutes: Long, seconds: Long): String = s"${f"$minutes%02d"}:${f"$seconds%02d"}"
  }

}


