package it.parttimeteam.view.startup.scenes

import it.parttimeteam.view.utils.{ScalavelliButton, ScalavelliLabel, Strings}
import scalafx.application.Platform
import scalafx.geometry.Pos.BottomRight
import scalafx.scene.control.{Button, Label, ProgressIndicator}
import scalafx.scene.layout.HBox

/**
  * Bottom bar for each Startup Scene
  */
trait StartupSceneBottomBar extends HBox with BaseStartupFormScene {
  /**
    * Show progress
    */
  def showLoading(): Unit

  /**
    * Hide progress
    */
  def hideLoading(): Unit
}

object StartupSceneBottomBar {

  class StartupSceneBottomBarImpl(onSubmit: () => Unit) extends StartupSceneBottomBar {
    alignment = BottomRight

    val btnSubmit: Button = ScalavelliButton(Strings.SEND, () => onSubmit())
    val progress: ProgressIndicator = new ProgressIndicator()
    val messageContainer: Label = ScalavelliLabel()

    progress.prefHeight <== height

    children.addAll(messageContainer, progress, btnSubmit)

    override def enableActions(): Unit = btnSubmit.setDisable(false)

    override def showLoading(): Unit = progress.setVisible(true)

    override def hideLoading(): Unit = progress.setVisible(false)

    override def disableActions(): Unit = btnSubmit.setDisable(true)

    override def showMessage(message: String): Unit = {
      Platform.runLater({
        messageContainer.setText(message)
        messageContainer.setVisible(true)
      })
    }

    override def hideMessage(): Unit = {
      Platform.runLater({
        messageContainer.setText("")
        messageContainer.setVisible(false)
      })
    }

    override def resetScreen(): Unit = {
      enableActions()
      hideLoading()
      hideMessage()
    }
  }

}

