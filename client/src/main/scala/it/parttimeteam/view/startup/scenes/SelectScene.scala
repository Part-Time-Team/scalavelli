package it.parttimeteam.view.startup.scenes

import it.parttimeteam.view.ViewConfig
import it.parttimeteam.view.startup.listeners.SelectSceneListener
import it.parttimeteam.view.utils.MachiavelliButton
import scalafx.geometry.Pos.Center
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{BorderPane, VBox}
import scalafx.stage.Stage

/**
  * Scene which allows to select the start up modality.
  *
  * @param parentStage the stage which contains the scene
  * @param listener    the listener which allow to select the modality
  */
class SelectScene(val parentStage: Stage, val listener: SelectSceneListener) extends Scene() {
  val background: ImageView = new ImageView(new Image("/images/background.png")) {
    fitWidth <== parentStage.width
    fitHeight <== parentStage.height
  }

  val title: ImageView = new ImageView(new Image("/images/game_title.png")) {
    fitWidth <== parentStage.width / 3
    preserveRatio = true
  }

  val center: VBox = new VBox()
  center.alignment = Center
  center.spacing = ViewConfig.formSpacing
  center.setMaxWidth(ViewConfig.formWidth)

  val btnPublicGame: Button = MachiavelliButton("Start new game", listener.onSelectedPublicGame)
  val btnPrivateGame: Button = MachiavelliButton("Participate with a code", listener.onSelectedPrivateGame)
  val btnCreatePrivateGame: Button = MachiavelliButton("Create new code", listener.onSelectedCreatePrivateGame)

  btnPublicGame.prefWidth <== center.width
  btnPrivateGame.prefWidth <== center.width
  btnCreatePrivateGame.prefWidth <== center.width

  val borderPane: BorderPane = new BorderPane()
  borderPane.prefWidth <== parentStage.width
  borderPane.prefHeight <== parentStage.height
  borderPane.center = center

  center.getChildren.addAll(title, btnPublicGame, btnPrivateGame, btnCreatePrivateGame)

  content = Seq(background, borderPane)
}
