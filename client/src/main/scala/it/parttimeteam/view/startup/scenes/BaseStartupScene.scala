package it.parttimeteam.view.startup.scenes

import it.parttimeteam.view.ViewConfig
import it.parttimeteam.view.utils.ImagePaths
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

  val background: ImageView = new ImageView(new Image(ImagePaths.STARTUP_BACKGROUND)) {
    fitWidth <== parentStage.width
    fitHeight <== parentStage.height
  }

  val mainContent: BorderPane = new BorderPane()

  mainContent.prefWidth <== parentStage.width
  mainContent.maxHeight <== parentStage.height
  mainContent.setPadding(Insets(ViewConfig.SCREEN_PADDING))

  val rootContent = new StackPane()
  rootContent.getChildren.addAll(background, mainContent)
  root = rootContent
}

