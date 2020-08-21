package it.parttimeteam.view.startup.scenes

import it.parttimeteam.view.startup.listeners.StartUpSceneListener
import it.parttimeteam.view.utils.MachiavelliButton
import scalafx.scene.control.Button
import scalafx.scene.layout.HBox

class StartUpSceneTopBar(listener: StartUpSceneListener) extends HBox {
  val btnBack: Button = MachiavelliButton("<", () => listener.onBackPressed())

  children.add(btnBack)
}
