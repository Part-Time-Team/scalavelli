package it.parttimeteam.view.startup.scenes

import it.parttimeteam.view.ViewConfig
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{BorderPane, StackPane}
import scalafx.stage.Stage

/**
  * Extend by all the StartupScenes
  *
  * @param parentStage the parent stage
  */
abstract class BaseStartupScene(parentStage: Stage) extends Scene() {

  val background: ImageView = new ImageView(new Image("/images/background.png")) {
    fitWidth <== parentStage.width
    fitHeight <== parentStage.height
  }
  background.preserveRatio = true

  val mainContent: BorderPane = new BorderPane()

  mainContent.prefWidth <== parentStage.width
  mainContent.maxHeight <== parentStage.height
  mainContent.setPadding(Insets(ViewConfig.screenPadding))

  val rootContent = new StackPane()
  rootContent.getChildren.addAll(background, mainContent)
  root = rootContent
}

