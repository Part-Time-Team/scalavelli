package it.parttimeteam.view.startup.scenes

import it.parttimeteam.view.ViewConfig
import it.parttimeteam.view.startup.listeners.SelectSceneListener
import it.parttimeteam.view.utils.{ImagePaths, ScalavelliButton, Strings}
import scalafx.geometry.Pos.Center
import scalafx.scene.control.Button
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.VBox
import scalafx.stage.Stage

/**
  * Scene which allows to select the start up modality.
  *
  * @param parentStage the stage which contains the scene
  * @param listener    the listener which allow to select the modality
  */
class SelectScene(val parentStage: Stage, val listener: SelectSceneListener) extends BaseStartupScene(parentStage) {
  val title: ImageView = new ImageView(new Image(ImagePaths.GAME_TITLE)) {
    fitWidth <== parentStage.width / 3
    preserveRatio = true
  }

  val center: VBox = new VBox()
  center.alignment = Center
  center.spacing = ViewConfig.FORM_SPACING
  center.setMaxWidth(ViewConfig.FORM_WIDTH)

  val btnPublicGame: Button = ScalavelliButton(Strings.START_NEW_GAME, () => listener.onSelectedPublicGame())
  val btnPrivateGame: Button = ScalavelliButton(Strings.JOIN_WITH_CODE, () => listener.onSelectedPrivateGame())
  val btnCreatePrivateGame: Button = ScalavelliButton(Strings.CREATE_PRIVATE_CODE, () => listener.onSelectedCreatePrivateGame())

  btnPublicGame.prefWidth <== center.width
  btnPrivateGame.prefWidth <== center.width
  btnCreatePrivateGame.prefWidth <== center.width

  mainContent.center = center

  center.getChildren.addAll(title, btnPublicGame, btnPrivateGame, btnCreatePrivateGame)

  content = Seq(background, mainContent)

}
