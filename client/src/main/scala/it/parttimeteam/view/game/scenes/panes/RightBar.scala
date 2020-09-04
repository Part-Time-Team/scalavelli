package it.parttimeteam.view.game.scenes.panes

import it.parttimeteam.gamestate.Opponent
import it.parttimeteam.view.game.listeners.GameSceneListener
import it.parttimeteam.view.utils.{MachiavelliButton, MachiavelliLabel}
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{BorderPane, HBox, TilePane, VBox}

/**
  * Pane which contains turn and other players information.
  * Allows to navigate turn history and pass the turn to an other player.
  */
trait RightBar extends BorderPane with BaseGamePane {

  /** Sets other player information, like name and number of available cards in hand.
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
}

object RightBar {

  class RightBarImpl(val listener: GameSceneListener) extends RightBar {
    padding = Insets(10d)

    val rightTop = new VBox()
    val rightBottom = new VBox()

    val stateContainer = new VBox()

    val messageLabel = MachiavelliLabel()

    val timerLabel = MachiavelliLabel("Timer 03:00")
    hideTimer()

    stateContainer.children.addAll(messageLabel, timerLabel)

    val otherPlayersContainer = new TilePane()
    otherPlayersContainer.setPrefColumns(2)

    val btnContainer = new VBox()
    btnContainer.spacing = 5d

    val undoBtn = MachiavelliButton("Undo", () => undoClick(), "images/undo.png", 15d, 80d)
    val redoBtn = MachiavelliButton("Redo", () => redoClick(), "images/redo.png", 15d, 80d)
    val nextBtn = MachiavelliButton("Next", () => endTurnClick())

    nextBtn.prefWidth <== rightBottom.width

    val historyBtnContainer = new HBox()
    historyBtnContainer.prefWidth <== btnContainer.width
    historyBtnContainer.alignment = Pos.Center
    historyBtnContainer.spacing = 5d

    historyBtnContainer.children.addAll(undoBtn, redoBtn)
    btnContainer.children.addAll(nextBtn, historyBtnContainer)

    rightTop.children.addAll(stateContainer, otherPlayersContainer)
    rightBottom.children.add(btnContainer)

    top = rightTop
    bottom = rightBottom

    /** @inheritdoc*/
    override def setOtherPlayers(otherPlayers: Seq[Opponent]): Unit = {
      for (player: Opponent <- otherPlayers) {
        val nameLabel = MachiavelliLabel(player.name)
        val cardsNumberLabel = MachiavelliLabel(player.cardsNumber.toString)

        val playerInfoContainer: VBox = new VBox()
        val cardInfoContainer: HBox = new HBox()

        val cardImage: ImageView = new ImageView(new Image("images/cards/backBlue.png"))
        cardImage.fitWidth = 20d
        cardImage.preserveRatio = true

        cardInfoContainer.children.addAll(cardImage, cardsNumberLabel)
        playerInfoContainer.children.addAll(nameLabel, cardInfoContainer)

        otherPlayersContainer.children.add(playerInfoContainer)
      }
    }

    /** @inheritdoc*/
    override def setMessage(message: String): Unit = {
      messageLabel.text = message
    }

    /** @inheritdoc*/
    override def showTimer(): Unit = {
      timerLabel.visible = true
    }

    /** @inheritdoc*/
    override def hideTimer(): Unit = {
      timerLabel.visible = false
    }

    /** @inheritdoc*/
    override def disableActions(): Unit = {
      undoBtn.setDisable(true)
      redoBtn.setDisable(true)
      nextBtn.setDisable(true)
    }

    /** @inheritdoc*/
    override def enableActions(): Unit = {
      undoBtn.setDisable(false)
      redoBtn.setDisable(false)
      nextBtn.setDisable(false)
    }

    private def redoClick(): Unit = {
      println("redoClick")
      listener.nextState()
    }

    private def undoClick(): Unit = {
      println("undoClick")
      listener.previousState()
    }

    private def endTurnClick(): Unit = {
      println(s"nextTurnClick")
      listener.endTurn()
    }
  }

}

