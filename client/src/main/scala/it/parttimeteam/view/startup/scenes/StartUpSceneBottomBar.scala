package it.parttimeteam.view.startup.scenes

import it.parttimeteam.view.utils.{MachiavelliButton, MachiavelliLabel}
import scalafx.application.Platform
import scalafx.geometry.Pos.BottomRight
import scalafx.scene.control.{Button, Label, ProgressIndicator}
import scalafx.scene.layout.HBox

class StartUpSceneBottomBar(onSubmit: () => Unit) extends HBox {
  alignment = BottomRight

  val btnSubmit: Button = MachiavelliButton("Send", () => onSubmit())
  val progress: ProgressIndicator = new ProgressIndicator()
  val messageContainer: Label = MachiavelliLabel()

  progress.prefHeight <== height

  children.addAll(messageContainer, progress, btnSubmit)

  def disableButtons(): Unit = btnSubmit.setDisable(true)

  def enableButtons(): Unit = btnSubmit.setDisable(false)

  def showLoading(): Unit = progress.setVisible(true)

  def hideLoading(): Unit = progress.setVisible(false)

  def showMessage(message: String): Unit = {
    Platform.runLater({
      messageContainer.setText(message)
      messageContainer.setVisible(true)
    })
  }

  def hideMessage(): Unit = {
    Platform.runLater({
      messageContainer.setText("")
      messageContainer.setVisible(false)
    })
  }

  def reset(): Unit = {
    enableButtons()
    hideLoading()
    hideMessage()
  }
}
