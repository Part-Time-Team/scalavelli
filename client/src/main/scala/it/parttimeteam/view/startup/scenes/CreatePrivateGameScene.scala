package it.parttimeteam.view.startup.scenes

import it.parttimeteam.view.BaseScene
import it.parttimeteam.view.startup.listeners.CreatePrivateGameSceneListener
import scalafx.scene.control.Button
import scalafx.stage.Stage

class CreatePrivateGameScene(override val parentStage: Stage, val listener: CreatePrivateGameSceneListener) extends BaseScene(parentStage) {
  val btn: Button = new Button("Create")
  btn.onAction = _ => listener.createPrivateGame("asd", 5)
  content = List(btn)
}
