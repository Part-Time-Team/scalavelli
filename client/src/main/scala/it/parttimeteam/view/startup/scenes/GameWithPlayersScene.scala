package it.parttimeteam.view.startup.scenes

import it.parttimeteam.view.BaseScene
import it.parttimeteam.view.startup.listeners.GameWithPlayersSceneListener
import scalafx.scene.control.Button
import scalafx.stage.Stage

class GameWithPlayersScene(override val parentStage: Stage, val listener: GameWithPlayersSceneListener) extends BaseScene(parentStage) {
  val btn: Button = new Button("Game with n players")
  btn.onAction = _ => listener.registerToGame("asd", 2)
  content = List(btn)
}
