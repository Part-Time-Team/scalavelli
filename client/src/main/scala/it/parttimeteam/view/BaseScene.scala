package it.parttimeteam.view

import scalafx.scene.Scene
import scalafx.scene.control.ProgressIndicator
import scalafx.stage.Stage

abstract case class BaseScene(parentStage: Stage) extends Scene {
  val progress: ProgressIndicator

  def showLoading(): Unit = {
    progress.setVisible(true)
  }

  def hideLoading(): Unit = {
    progress.setVisible(false)
  }
}
