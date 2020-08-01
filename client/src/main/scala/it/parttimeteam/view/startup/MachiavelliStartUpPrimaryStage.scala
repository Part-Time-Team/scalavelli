package it.parttimeteam.view.startup

import it.parttimeteam.controller.startup.GameStartUpListener
import it.parttimeteam.view.startup.listeners._
import it.parttimeteam.view.startup.scenes._
import it.parttimeteam.view.{ViewConfig, ViewEvent}
import scalafx.application.JFXApp


/**
  * Stage for startup scenes.
  */
trait MachiavelliStartUpPrimaryStage extends JFXApp.PrimaryStage with PrimaryStageListener{
  def notifyPrivateCode(privateCode: String): Unit

  def notifyLobbyJoined(): Unit
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
  val publicGameScene: PublicGameStartUpScene = new PublicGameStartUpScene(this)
  val privateGameScene: PrivateGameStartUpScene = new PrivateGameStartUpScene(this)
  val createPrivateGame: CreatePrivateGameStartUpSceneImpl = new CreatePrivateGameStartUpSceneImpl(this)

  var currentInnerScene: BaseStartUpScene = _

  private val mainScene = new SelectScene(this, new SelectSceneListener {

    override def onSelectedPublicGame(): Unit = setCurrentScene(publicGameScene)

    override def onSelectedPrivateGame(): Unit = setCurrentScene(privateGameScene)

    override def onSelectedCreatePrivateGame(): Unit = setCurrentScene(createPrivateGame)
  })

  scene = mainScene


  onCloseRequest = _ => {
    System.exit(0)
  }

  def setCurrentScene(newScene: BaseStartUpScene): Unit = {
    scene = newScene
    currentInnerScene = newScene
  }

  // View actions
  override def onBackPressed(): Unit = {
    scene = mainScene
  }

  override def onSubmit(viewEvent: ViewEvent): Unit = {
    gameStartUpListener.onViewEvent(viewEvent)
  }

  // Controller actions
  override def notifyPrivateCode(privateCode: String): Unit = {
    createPrivateGame.showCode(privateCode)
  }

  override def notifyLobbyJoined(): Unit = {
    currentInnerScene.showMessage("Waiting for other players")
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
