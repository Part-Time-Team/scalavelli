package it.parttimeteam.view.startup.scenes

import scalafx.stage.Stage

abstract class BaseStartupFormScene(val parentStage: Stage) extends BaseStartupScene(parentStage) {

  def showMessage(message: String): Unit

  def hideMessage(): Unit

  def disableButtons(): Unit

  def resetScreen(): Unit
}
