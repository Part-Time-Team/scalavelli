package it.parttimeteam.view.startup.scenes

import it.parttimeteam.view.{BaseScene, SelectSceneListener}
import scalafx.scene.control.Button
import scalafx.stage.Stage

class SelectScene(override val parentStage: Stage, val listener: SelectSceneListener) extends BaseScene(parentStage) {
  val btn: Button = new Button("Main")
  btn.onAction = _ => listener.onSelectedGameWithPlayers()
  content = List(btn)
}
