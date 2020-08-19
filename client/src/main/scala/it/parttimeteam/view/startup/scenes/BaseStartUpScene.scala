package it.parttimeteam.view.startup.scenes

import scalafx.application.Platform
import scalafx.scene.Scene
import scalafx.scene.control.{Label, ProgressIndicator}

trait BaseStartUpScene extends Scene {

  val progress: ProgressIndicator
  val messageContainer: Label

  def showLoading(): Unit = {
    progress.setVisible(true)
  }

  def hideLoading(): Unit = {
    progress.setVisible(false)
  }

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
}
