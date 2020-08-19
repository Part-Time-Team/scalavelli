package it.parttimeteam.view.game

import it.parttimeteam.view.ViewConfig
import it.parttimeteam.view.game.listeners.GameSceneListener
import it.parttimeteam.view.game.scenes.GameScene
import it.parttimeteam.view.utils.MachiavelliAlert
import scalafx.application.JFXApp
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType

/**
  * Stage for game scenes
  */
trait MachiavelliGamePrimaryStage extends JFXApp.PrimaryStage with GameStageListener {

  def notifyError(result:String): Unit
}

/**
  * Stage for game scenes
  *
  * @param gameListener        enables to call actions exposed by controller
  * @param windowWidth         the width of app window
  * @param windowHeight        the height of app window  */
class MachiavelliGamePrimaryStageImpl(gameListener: GameStageListener, windowWidth: Double, windowHeight: Double) extends MachiavelliGamePrimaryStage{
  title = "Machiavelli"
  resizable = true
  width = windowWidth
  height = windowHeight

  val stage: MachiavelliGamePrimaryStage = this

  val gameScene: GameScene = new GameScene(this)

  scene = gameScene

  onCloseRequest = _ => {
    System.exit(0)
  }

  override def notifyError(result:String): Unit = {
    val alert: Alert = MachiavelliAlert("Error", result, AlertType.Error)
    alert.showAndWait()
  }

}

/**
  * Companion object for MachiavelliGamePrimaryStage
  */
object MachiavelliGamePrimaryStage {
  val windowWidth: Double = ViewConfig.screenWidth
  val windowHeight: Double = ViewConfig.screenHeight

  def apply(listener: GameStageListener): MachiavelliGamePrimaryStage = new MachiavelliGamePrimaryStageImpl(listener, windowWidth, windowHeight)

  def apply(): MachiavelliGamePrimaryStage = new MachiavelliGamePrimaryStageImpl(null, windowWidth, windowHeight)
}

trait GameStageListener extends GameSceneListener
