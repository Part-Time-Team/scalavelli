package it.parttimeteam.view.startup.scenes

import scalafx.stage.Stage

/**
  * Class extended by each StartupFormScene
  *
  * @param parentStage the parent stage
  */
abstract class BaseStartupFormScene(val parentStage: Stage) extends BaseStartupScene(parentStage) with StartupFormScene {
  
}
