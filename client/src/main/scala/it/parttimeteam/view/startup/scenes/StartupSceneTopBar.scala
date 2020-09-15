package it.parttimeteam.view.startup.scenes

import it.parttimeteam.view.startup.listeners.StartupSceneListener
import it.parttimeteam.view.utils.ScalavelliButton
import scalafx.scene.control.Button
import scalafx.scene.layout.HBox

/**
  * Bottom bar for each Startup Scene
  */
trait StartupSceneTopBar extends HBox {

}


object StartupSceneTopBar {

  class StartupSceneTopBarImpl(listener: StartupSceneListener) extends StartupSceneTopBar {
    val btnBack: Button = ScalavelliButton("<", () => listener.onBackPressed())

    children.add(btnBack)
  }

}