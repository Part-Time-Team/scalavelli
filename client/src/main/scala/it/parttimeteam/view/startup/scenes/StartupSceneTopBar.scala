package it.parttimeteam.view.startup.scenes

import it.parttimeteam.view.startup.listeners.StartupSceneListener
import it.parttimeteam.view.utils.MachiavelliButton
import scalafx.scene.control.Button
import scalafx.scene.layout.HBox

class StartupSceneTopBar(listener: StartupSceneListener) extends HBox {
  val btnBack: Button = MachiavelliButton("<", () => listener.onBackPressed())

  children.add(btnBack)
}
