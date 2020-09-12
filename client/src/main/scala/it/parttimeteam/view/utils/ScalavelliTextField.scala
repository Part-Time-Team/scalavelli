package it.parttimeteam.view.utils

import scalafx.scene.control.TextField

/**
  * Builder for a default TextField
  */
object ScalavelliTextField {

  def apply(promptText: String): TextField = {
    val textField: TextField = new TextField()
    textField.setPromptText(promptText)
    textField
  }
}
