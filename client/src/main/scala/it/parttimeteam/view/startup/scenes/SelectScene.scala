package it.parttimeteam.view.startup.scenes

import it.parttimeteam.view.utils.ButtonFactory
import it.parttimeteam.view.BaseScene
import it.parttimeteam.view.startup.listeners.SelectSceneListener
import scalafx.geometry.Pos.Center
import scalafx.scene.control.Button
import scalafx.scene.layout.{BorderPane, VBox}
import scalafx.stage.Stage

class SelectScene(override val parentStage: Stage, val listener: SelectSceneListener) extends BaseScene(parentStage) {
  val btnPlayersNum: Button = ButtonFactory.makeButton("Start new game", listener.onSelectedGameWithPlayers, 200d)
  val btnCode: Button = ButtonFactory.makeButton("Participate with a code", listener.onSelectedGameWithCode, 200d)
  val btnNewCode: Button = ButtonFactory.makeButton("Create new code", listener.onSelectedCreatePrivateGame, 200d)

  val borderPane: BorderPane = new BorderPane()

  val center: VBox = new VBox()
  center.alignment = Center
  center.spacing = 20d

  borderPane.center = center

  center.getChildren.addAll(btnPlayersNum, btnCode, btnNewCode)

  root = borderPane
}
