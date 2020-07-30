package it.parttimeteam.view.startup.scenes

import it.parttimeteam.view.startup.listeners.SelectSceneListener
import it.parttimeteam.view.utils.ButtonFactory
import scalafx.geometry.Pos.Center
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{BorderPane, VBox}
import scalafx.stage.Stage

class SelectScene(val parentStage: Stage, val listener: SelectSceneListener) extends Scene() {
  val background: ImageView = new ImageView(new Image("/images/background.png")) {
    fitWidth <== parentStage.width
    fitHeight <== parentStage.height
  }

  val title: ImageView = new ImageView(new Image("/images/game_title.png")) {
    fitWidth <== parentStage.width / 3
    preserveRatio = true
  }

  val btnPlayersNum: Button = ButtonFactory.makeButton("Start new game", listener.onSelectedGameWithPlayers, 200d)
  val btnCode: Button = ButtonFactory.makeButton("Participate with a code", listener.onSelectedGameWithCode, 200d)
  val btnNewCode: Button = ButtonFactory.makeButton("Create new code", listener.onSelectedCreatePrivateGame, 200d)

  val borderPane: BorderPane = new BorderPane()
  borderPane.prefWidth <== parentStage.width
  borderPane.prefHeight <== parentStage.height

  val center: VBox = new VBox()
  center.alignment = Center
  center.spacing = 20d

  borderPane.center = center

  center.getChildren.addAll(title, btnPlayersNum, btnCode, btnNewCode)

  content = Seq(background, borderPane)
}
