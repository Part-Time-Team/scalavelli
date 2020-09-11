package it.parttimeteam.view.game.scenes.panes

import it.parttimeteam.gamestate.Opponent
import it.parttimeteam.view.game.listeners.GameSceneListener
import it.parttimeteam.view.game.scenes.panes.HistoryNavigationPane.HistoryNavigationPaneImpl
import it.parttimeteam.view.game.scenes.panes.OtherPlayersPane.OtherPlayersPaneImpl
import it.parttimeteam.view.game.scenes.panes.TimerPane.TimerPaneImpl
import it.parttimeteam.view.utils.{MachiavelliButton, MachiavelliLabel}
import scalafx.geometry.Insets
import scalafx.scene.layout.{BorderPane, VBox}

/**
  * Pane which contains turn and other players information.
  * Allows to navigate turn history and pass the turn to an other player.
  */
trait GameInfoBar extends BorderPane with ActionGamePane {
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
    */
  def showTimer(): Unit

  /**
    * Hide the timer countdown.
    */
  def hideTimer(): Unit

  /**
    * Enables/disables the next button
    *
    * @param enabled if the button should be enabled or not
    */
  def setNextEnabled(enabled: Boolean): Unit
}

trait HistoryNavigationListener {
  def onUndoClick(): Unit

  def onRedoClick(): Unit

  def onResetClick(): Unit
}

object GameInfoBar {

  class GameInfoBarImpl(val listener: GameSceneListener) extends GameInfoBar {
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
    btnContainer.spacing = 5d

    historyNavigationPane.prefWidth <== btnContainer.width

    val rightTop = new VBox()
    val rightBottom = new VBox()

    val stateContainer = new VBox()

    val messageLabel = MachiavelliLabel()

    stateContainer.children.addAll(messageLabel, timerPane)

    timerPane.hide()

    val nextBtn = MachiavelliButton("Pass", () => listener.endTurn())
    val leaveBtn = MachiavelliButton("Leave Game", () => listener.leaveGame())

    nextBtn.prefWidth <== rightBottom.width
    leaveBtn.prefWidth <== rightBottom.width

    btnContainer.children.addAll(nextBtn, leaveBtn, historyNavigationPane)

    rightTop.children.addAll(stateContainer, otherPlayersPane)
    rightBottom.children.add(btnContainer)

    top = rightTop
    bottom = rightBottom

    /** @inheritdoc*/
    override def setMessage(message: String): Unit = messageLabel.text = message


    /** @inheritdoc*/
    override def showTimer(): Unit = timerPane.hide()

    /** @inheritdoc*/
    override def hideTimer(): Unit = timerPane.show()

    /** @inheritdoc*/
    override def disableActions(): Unit = {
      historyNavigationPane.disableActions()
      nextBtn.setDisable(true)
    }

    /** @inheritdoc*/
    override def enableActions(): Unit = {
      nextBtn.setDisable(false)
      historyNavigationPane.enableActions()
    }

    /** @inheritdoc*/
    override def setNextEnabled(enabled: Boolean): Unit = nextBtn.setDisable(!enabled)

    /** @inheritdoc*/
    override def setOtherPlayers(opponents: Seq[Opponent]): Unit = otherPlayersPane.setOtherPlayers(opponents)

    /** @inheritdoc*/
    override def setResetEnabled(enabled: Boolean): Unit = historyNavigationPane.setResetEnabled(enabled)

    /** @inheritdoc*/
    override def setRedoEnabled(enabled: Boolean): Unit = historyNavigationPane.setRedoEnabled(enabled)

    /** @inheritdoc*/
    override def setUndoEnabled(enabled: Boolean): Unit = historyNavigationPane.setUndoEnabled(enabled)

    /** @inheritdoc */
    override def setNextText(text: String): Unit = {
      nextBtn.setText(text)
    }
  }

}