package it.parttimeteam.view.startup.scenes

import it.parttimeteam.view.BaseScene
import it.parttimeteam.view.startup.listeners.PrivateGameSceneListener
import scalafx.scene.control.Button
import scalafx.stage.Stage

class PrivateGameScene(override val parentStage: Stage, val listener: PrivateGameSceneListener) extends BaseScene(parentStage) {
  val btn: Button = new Button("Private")
  btn.onAction = _ => listener.registerToPrivateGame("","")
  content = List(btn)
}
