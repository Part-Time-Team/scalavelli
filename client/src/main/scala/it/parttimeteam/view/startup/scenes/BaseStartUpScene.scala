package it.parttimeteam.view.startup.scenes

import scalafx.scene.Scene

trait BaseStartUpScene extends Scene {

  def showMessage(message: String): Unit

  def hideMessage(): Unit
}
