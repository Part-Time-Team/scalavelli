package it.parttimeteam.view.game.scenes.panes

import it.parttimeteam.gamestate.Opponent
import it.parttimeteam.view.ViewConfig
import it.parttimeteam.view.game.scenes.panes.HistoryNavigationPane.HistoryNavigationPaneImpl
import it.parttimeteam.view.game.scenes.panes.OtherPlayersPane.OtherPlayersPaneImpl
import it.parttimeteam.view.game.scenes.panes.TimerPane.TimerPaneImpl
import it.parttimeteam.view.utils.{ScalavelliButton, ScalavelliLabel, Strings}
import scalafx.geometry.Insets
import scalafx.scene.layout.{BorderPane, VBox}

/**
  * Pane which contains turn and other players information.
  * Allows to navigate turn history and pass the turn to an other player.
  */
trait SidePane extends BorderPane with ActionGamePane {

  /**
    * Hide the timer countdown.
    *
    * @return
    */
  def hideTimer(): Unit

  /**
    * Updates the next button text
    *
    * @param text the new text
    */
  def setNextText(text: String): Unit

  /**
    * Enables/disables the reset button
    *
    * @param enabled if the button should be enabled or not
    */
  def setResetEnabled(enabled: Boolean): Unit

  /**
    * Enables/disables the redo button
    *
    * @param enabled if the button should be enabled or not
    */
  def setRedoEnabled(enabled: Boolean): Unit

  /**
    * Enables/disables the undo button
    *
    * @param enabled if the button should be enabled or not
    */
  def setUndoEnabled(enabled: Boolean): Unit

  /**
    * Sets other player information, like name and number of available cards in hand.
    *
    * @param opponents the sequence of other players information
    */
  def setOtherPlayers(opponents: Seq[Opponent]): Unit

  /**
    * Display a message inside the RightBar.
    *
    * @param message the message to be displayed
    */
  def setMessage(message: String): Unit

  /**
    * Make the timer countdown visible.
    *
    * @param minutes countdown minutes
    * @param seconds countdown seconds
    */
  def showTimer(minutes: Long, seconds: Long): Unit

  /**
    * Update countdown.
    *
    * @param minutes countdown minutes
    * @param seconds countdown seconds
    */
  def updateTimer(minutes: Long, seconds: Long): Unit

  /**
    * Show timer end message
    *
    * @return
    */
  def notifyTimerEnd(): Unit

  /**
    * Enables/disables the next button
    *
    * @param enabled if the button should be enabled or not
    */
  def setNextEnabled(enabled: Boolean): Unit
}

trait GameInfoBarListener {
  /**
    * The player ends his turn.
    */
  def endTurn(): Unit

  /**
    * The player leaves the game.
    */
  def leaveGame(): Unit

  /**
    * The player goes forward in turn history.
    */
  def nextState(): Unit

  /**
    * The player goes backward in turn history.
    */
  def previousState(): Unit

  /**
    * The player goes back to initial turn state.
    */
  def resetState(): Unit
}


object SidePane {

  class SidePaneImpl(val listener: GameInfoBarListener) extends SidePane {
    padding = Insets(10d)

    this.getStyleClass.add("woodBack")

    val otherPlayersPane: OtherPlayersPane = new OtherPlayersPaneImpl()
    val timerPane: TimerPane = new TimerPaneImpl()

    val historyNavigationPane: HistoryNavigationPane = new HistoryNavigationPaneImpl(new HistoryNavigationListener {
      override def onUndoClick(): Unit = listener.previousState()

      override def onRedoClick(): Unit = listener.nextState()

      override def onResetClick(): Unit = listener.resetState()
    })

    val btnContainer = new VBox()
    btnContainer.spacing = ViewConfig.DEFAULT_SPACING

    historyNavigationPane.prefWidth <== btnContainer.width

    val rightTop = new VBox()
    val rightBottom = new VBox()

    val stateContainer = new VBox()

    val messageLabel = ScalavelliLabel()

    timerPane.hide()
    stateContainer.children.addAll(messageLabel, timerPane)

    val nextBtn = ScalavelliButton(Strings.PASS_BTN, () => listener.endTurn())
    val leaveBtn = ScalavelliButton(Strings.LEAVE_GAME_BTN, () => listener.leaveGame())

    nextBtn.prefWidth <== rightBottom.width
    leaveBtn.prefWidth <== rightBottom.width

    btnContainer.children.addAll(nextBtn, leaveBtn, historyNavigationPane)

    rightTop.children.addAll(stateContainer, otherPlayersPane)
    rightBottom.children.add(btnContainer)

    top = rightTop
    bottom = rightBottom

    /** @inheritdoc */
    override def setMessage(message: String): Unit = messageLabel.text = message

    /** @inheritdoc */
    override def disableActions(): Unit = {
      historyNavigationPane.disableActions()
      nextBtn.setDisable(true)
    }

    /** @inheritdoc */
    override def enableActions(): Unit = {
      nextBtn.setDisable(false)
      historyNavigationPane.enableActions()
    }

    /** @inheritdoc */
    override def setNextEnabled(enabled: Boolean): Unit = nextBtn.setDisable(!enabled)

    /** @inheritdoc */
    override def setOtherPlayers(opponents: Seq[Opponent]): Unit = otherPlayersPane.setOtherPlayers(opponents)

    /** @inheritdoc */
    override def setResetEnabled(enabled: Boolean): Unit = historyNavigationPane.setResetEnabled(enabled)

    /** @inheritdoc */
    override def setRedoEnabled(enabled: Boolean): Unit = historyNavigationPane.setRedoEnabled(enabled)

    /** @inheritdoc */
    override def setUndoEnabled(enabled: Boolean): Unit = historyNavigationPane.setUndoEnabled(enabled)

    /** @inheritdoc*/
    override def setNextText(text: String): Unit = {
      nextBtn.setText(text)
    }

    /** @inheritdoc*/
    override def showTimer(minutes: Long, seconds: Long): Unit = timerPane.show(minutes, seconds)

    /** @inheritdoc*/
    override def updateTimer(minutes: Long, seconds: Long): Unit = timerPane.set(minutes, seconds)

    /** @inheritdoc*/
    override def notifyTimerEnd(): Unit = timerPane.displayMessage(Strings.TIME_IS_UP)

    /** @inheritdoc*/
    override def hideTimer(): Unit = timerPane.hide()
  }

}