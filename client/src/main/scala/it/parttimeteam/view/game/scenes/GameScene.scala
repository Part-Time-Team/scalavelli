package it.parttimeteam.view.game.scenes

import it.parttimeteam.view.ViewConfig
import it.parttimeteam.view.game.listeners.GameSceneListener
import it.parttimeteam.view.utils.MachiavelliLabel
import scalafx.scene.Scene
import scalafx.scene.control._
import scalafx.scene.layout.BorderPane

class GameScene(val listener: GameSceneListener) extends Scene() {

  val label: Label = MachiavelliLabel("Hello Game", ViewConfig.formLabelFontSize)

  val borderPane: BorderPane = new BorderPane()

  borderPane.center = label

  root = borderPane

}
