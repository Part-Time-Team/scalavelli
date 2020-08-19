package it.parttimeteam.view.startup.scenes

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
    messageContainer.setText(message)
    messageContainer.setVisible(true)
  }

  def hideMessage(): Unit = {
    messageContainer.setText("")
    messageContainer.setVisible(false)
  }
}
