package it.parttimeteam.view.startup

import it.parttimeteam.controller.startup.GameStartUpListener
import it.parttimeteam.view.{ViewConfig, ViewEvent}
import it.parttimeteam.view.startup.listeners._
import it.parttimeteam.view.startup.scenes.{CreatePrivateGameSceneImpl, PrivateGameScene, PublicGameScene, SelectScene}
import scalafx.application.JFXApp


/**
  * Stage for startup scenes.
  */
trait MachiavelliStartUpPrimaryStage extends JFXApp.PrimaryStage with PrimaryStageListener{
  def setReceivedCode(code: String): Unit
}

/**
  * Stage for startup scenes.
  *
  * @param gameStartUpListener enables to call actions exposed by controller
  * @param windowWidth         the width of app window
  * @param windowHeight        the height of app window
  */
class MachiavelliStartUpPrimaryStageImpl(gameStartUpListener: GameStartUpListener, windowWidth: Double, windowHeight: Double) extends MachiavelliStartUpPrimaryStage{
  title = "Machiavelli"
  resizable = true
  width = windowWidth
  height = windowHeight

  val stage: MachiavelliStartUpPrimaryStage = this
  val publicGameScene: PublicGameScene = new PublicGameScene(this)
  val privateGameScene: PrivateGameScene = new PrivateGameScene(this)
  val createPrivateGame: CreatePrivateGameSceneImpl = new CreatePrivateGameSceneImpl(this)

  private val mainScene = new SelectScene(this, new SelectSceneListener {

    override def onSelectedPublicGame(): Unit = scene = publicGameScene

    override def onSelectedPrivateGame(): Unit = scene = privateGameScene

    override def onSelectedCreatePrivateGame(): Unit = scene = createPrivateGame
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

  override def setReceivedCode(code: String): Unit = {
    createPrivateGame.showCode(code)
    System.out.println("Stage - onCodeReceived")
  }
}

/**
  * Companion object for MachiavelliStartupPrimaryStage
  */
object MachiavelliStartUpPrimaryStage {
  val windowWidth: Double = ViewConfig.screenWidth
  val windowHeight: Double = ViewConfig.screenHeight

  def apply(listener: GameStartUpListener): MachiavelliStartUpPrimaryStage = new MachiavelliStartUpPrimaryStageImpl(listener, windowWidth, windowHeight)

  def apply(): MachiavelliStartUpPrimaryStage = new MachiavelliStartUpPrimaryStageImpl(null, windowWidth, windowHeight)
}

trait PrimaryStageListener extends StartUpSceneListener
