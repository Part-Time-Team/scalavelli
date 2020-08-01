package it.parttimeteam.view

import scalafx.scene.Scene
import scalafx.scene.control.ProgressIndicator

trait BaseScene extends Scene {

  val progress: ProgressIndicator

  def showLoading(): Unit = {
    progress.setVisible(true)
  }

  def hideLoading(): Unit = {
    progress.setVisible(false)
  }
}
