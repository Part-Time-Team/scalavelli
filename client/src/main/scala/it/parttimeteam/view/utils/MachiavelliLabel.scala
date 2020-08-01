package it.parttimeteam.view.utils

import it.parttimeteam.view.ViewConfig
import scalafx.scene.control.Label
import scalafx.scene.text.Font

/**
  * Builder for a default Label
  */
object MachiavelliLabel {

  def apply(text: String, fontSize:Double): Label = {
    val label:Label = Label(text)
    label.setFont(new Font(fontSize))
    label
  }

  def apply(text: String): Label = {
    val label = this (text, ViewConfig.baseFontSize)
    label
  }

  def apply(fontSize:Double): Label = {
    val label = this ("", fontSize)
    label
  }

  def apply(): Label = {
    val label = this ("")
    label
  }

}
