package it.parttimeteam.view.game.scenes.panes

import it.parttimeteam.view.utils.{ScalavelliLabel, Strings}
import scalafx.scene.layout.VBox

/**
  * Pane which show the countdown during user turn
  */
trait TimerPane extends VBox {

  /**
    * Display a message instead of countdown time
    *
    * @param message the message to be displayed
    */
  def displayMessage(message: String): Unit

  /**
    * Sets the actual countdown time
    *
    * @param minutes remaining minutes
    * @param seconds remaining seconds
    */
  def set(minutes: Long, seconds: Long): Unit

  /**
    *
    * Show the countdown with the starting time
    *
    * @param minutes starting minutes
    * @param seconds starting seconds
    */
  def show(startingMinutes: Long, startingSeconds: Long): Unit

  /**
    * Hide the countdown
    */
  def hide(): Unit
}

object TimerPane {

  class TimerPaneImpl extends TimerPane {
    val timerLabel = ScalavelliLabel(Strings.TIMER)
    timerLabel.getStyleClass.add("boldText")

    val countdownLabel = ScalavelliLabel()

    this.children.addAll(timerLabel, countdownLabel)

    /** @inheritdoc*/
    override def hide(): Unit = {
      visible = false
    }

    /** @inheritdoc*/
    override def displayMessage(message: String): Unit = {
      setText(message)
    }

    /** @inheritdoc*/
    override def set(minutes: Long, seconds: Long): Unit = setText(timeToLabel(minutes, seconds))

    /** @inheritdoc*/
    override def show(startingMinutes: Long, startingSeconds: Long): Unit = {
      visible = true
      setText(timeToLabel(startingMinutes, startingSeconds))
    }

    private def setText(message: String): Unit = countdownLabel.text = message

    private def timeToLabel(minutes: Long, seconds: Long): String = s"${f"$minutes%02d"}:${f"$seconds%02d"}"
  }

}


