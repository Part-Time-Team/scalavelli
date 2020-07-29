package it.parttimeteam.view.startup.scenes

import it.parttimeteam.view.{BaseScene, SelectSceneListener}
import scalafx.geometry.Pos.Center
import scalafx.scene.control.Button
import scalafx.scene.layout.{BorderPane, VBox}
import scalafx.stage.Stage

class SelectScene(override val parentStage: Stage, val listener: SelectSceneListener) extends BaseScene(parentStage) {
  val btnPlayersNum: Button = new Button("Start new game")
  btnPlayersNum.minWidth = 200d

  val btnCode: Button = new Button("Participate with a code")
  btnCode.minWidth = 200d

  val btnNewCode: Button = new Button("Create new code")
  btnNewCode.minWidth = 200d

  val borderPane: BorderPane = new BorderPane()
  val center: VBox = new VBox()
  center.alignment = Center
  center.spacing = 20d

  borderPane.center = center

  center.getChildren.addAll(btnPlayersNum, btnCode, btnNewCode)

  btnPlayersNum.onAction = _ => listener.onSelectedGameWithPlayers()
  btnCode.onAction = _ => listener.onSelectedGameWithCode()
  btnNewCode.onAction = _ => listener.onSelectedCreatePrivateGame()

  root = borderPane
}
