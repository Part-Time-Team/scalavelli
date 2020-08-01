package it.parttimeteam.view.startup

import it.parttimeteam.controller.startup.GameStartUpListener
import it.parttimeteam.view.ViewEvent
import it.parttimeteam.view.startup.listeners._
import it.parttimeteam.view.startup.scenes.{CreatePrivateGameScene, PrivateGameScene, PublicGameScene, SelectScene}
import scalafx.application.JFXApp

/**
  * Stage for startup scenes.
  *
  * @param gameStartUpListener enables to call actions exposed by controller
  * @param windowWidth         the width of app window
  * @param windowHeight        the height of app window
  */
class MachiavelliStartupPrimaryStage(gameStartUpListener: GameStartUpListener, windowWidth: Double, windowHeight: Double) extends JFXApp.PrimaryStage with PrimaryStageListener {
  title = "Machiavelli"
  resizable = true
  width = windowWidth
  height = windowHeight

  val stage: MachiavelliStartupPrimaryStage = this

  private val mainScene = new SelectScene(this, new SelectSceneListener {

    override def onSelectedPublicGame(): Unit = scene = new PublicGameScene(stage)

    override def onSelectedPrivateGame(): Unit = scene = new PrivateGameScene(stage)

    override def onSelectedCreatePrivateGame(): Unit = scene = new CreatePrivateGameScene(stage)
  })

  scene = mainScene

  onCloseRequest = _ => {
    System.exit(0)
  }

  override def onBackPressed(): Unit = {
    scene = mainScene
  }

  override def onSubmit(viewEvent: ViewEvent): Unit = {
    gameStartUpListener.onViewEvent(viewEvent)
  }
}

/**
  * Companion object for MachiavelliStartupPrimaryStage
  */
object MachiavelliStartupPrimaryStage {
  val windowWidth: Double = 800d
  val windowHeight: Double = 600d

  def apply(listener: GameStartUpListener): MachiavelliStartupPrimaryStage = new MachiavelliStartupPrimaryStage(listener, windowWidth, windowHeight)

  def apply(): MachiavelliStartupPrimaryStage = new MachiavelliStartupPrimaryStage(null, windowWidth, windowHeight)

}

trait PrimaryStageListener extends StartUpSceneListener
