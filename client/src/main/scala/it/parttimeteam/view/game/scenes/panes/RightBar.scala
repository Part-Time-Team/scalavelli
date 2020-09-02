package it.parttimeteam.view.game.scenes.panes

import it.parttimeteam.gamestate.Opponent
import it.parttimeteam.view.game.listeners.GameSceneListener
import it.parttimeteam.view.utils.{MachiavelliButton, MachiavelliLabel}
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{BorderPane, HBox, TilePane, VBox}

class RightBar(val listener: GameSceneListener) extends BorderPane
{
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

  val undoBtn = MachiavelliButton("Undo", () => undoClick(), "images/undo.png", 15d)
  val redoBtn = MachiavelliButton("Redo", () => redoClick(), "images/redo.png", 15d)
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

  def setOtherPlayers(otherPlayers: Seq[Opponent]): Unit = {
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

  def setMessage(message: String): Unit = {
    messageLabel.text = message
  }

  def showTimer(): Unit = {
    timerLabel.visible = true
  }

  def hideTimer(): Unit = {
    timerLabel.visible = false
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
