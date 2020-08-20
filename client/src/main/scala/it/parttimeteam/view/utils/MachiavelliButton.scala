package it.parttimeteam.view.utils

import scalafx.scene.control.Button
import scalafx.scene.image.{Image, ImageView}

/**
  * Builder for a default Button
  */
object MachiavelliButton {

  def apply(text: String, onClick: () => Unit): Button = {
    val btn: Button = new Button(text)
    btn.onAction = _ => onClick()
    btn
  }

  def apply(text: String, onClick: () => Unit, minWidth: Double): Button = {
    val btn = this (text, onClick)
    btn.setMinWidth(minWidth)
    btn
  }

  def apply(text: String, onClick: () => Unit, iconPath: String, iconHeight: Double): Button = {
    val btn = this (text, onClick)

    val img = new ImageView(new Image(iconPath))
    img.fitHeight = iconHeight
    img.preserveRatio = true

    btn.setGraphic(img)

    btn
  }
}
