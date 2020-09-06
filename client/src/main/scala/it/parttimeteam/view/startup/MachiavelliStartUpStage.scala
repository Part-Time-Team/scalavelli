package it.parttimeteam.view.startup

import it.parttimeteam.controller.startup.GameStartUpListener
import it.parttimeteam.view.startup.listeners._
import it.parttimeteam.view.startup.scenes._
import it.parttimeteam.view.utils.MachiavelliAlert
import it.parttimeteam.view.{BaseStage, ViewConfig}
import scalafx.application.Platform
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType


/**
  * Stage for startup scenes.
  */
trait MachiavelliStartUpStage extends BaseStage with PrimaryStageListener {
  def notifyPrivateCode(privateCode: String): Unit

  def notifyLobbyJoined(): Unit

  def notifyError(result: String): Unit
}

/**
  * Stage for startup scenes.
  *
  * @param gameStartUpListener enables to call actions exposed by controller
  */
class MachiavelliStartUpPrimaryStageImpl(gameStartUpListener: GameStartUpListener) extends MachiavelliStartUpStage {
  private val mainScene = new SelectScene(this, new SelectSceneListener {

    override def onSelectedPublicGame(): Unit = setCurrentScene(publicGameScene)

    override def onSelectedPrivateGame(): Unit = setCurrentScene(privateGameScene)

    override def onSelectedCreatePrivateGame(): Unit = setCurrentScene(createPrivateGame)
  })

  val stage: MachiavelliStartUpStage = this
  val publicGameScene: PublicGameStartUpScene = new PublicGameStartUpScene(this, this)
  val privateGameScene: PrivateGameStartUpScene = new PrivateGameStartUpScene(this, this)
  val createPrivateGame: CreatePrivateGameStartUpSceneImpl = new CreatePrivateGameStartUpSceneImpl(this, this)

  var currentInnerScene: BaseStartUpFormScene = _

  scene = mainScene

  onCloseRequest = _ => {
    System.exit(0)
  }

  def setCurrentScene(newScene: BaseStartUpFormScene): Unit = {
    scene = newScene
    currentInnerScene = newScene
  }

  // View actions
  override def onBackPressed(): Unit = {
    // TODO: Luca - Call leave lobby only when joined
    gameStartUpListener.onViewEvent(LeaveLobbyViewEvent)

    currentInnerScene.resetScreen()
    scene = mainScene
  }

  override def onSubmit(viewEvent: StartUpViewEvent): Unit = {
    gameStartUpListener.onViewEvent(viewEvent)
  }

  // Controller actions
  override def notifyPrivateCode(privateCode: String): Unit = {
    Platform.runLater(createPrivateGame.showCode(privateCode))
    notifyLobbyJoined()
  }

  override def notifyLobbyJoined(): Unit = {
    Platform.runLater(currentInnerScene.showMessage("Waiting for other players"))
  }

  override def notifyError(result: String): Unit = {
    val alert: Alert = MachiavelliAlert("Error", result, AlertType.Error)
    Platform.runLater(alert.showAndWait())
  }
}

/**
  * Companion object for MachiavelliStartupPrimaryStage
  */
object MachiavelliStartUpStage {
  val windowWidth: Double = ViewConfig.screenWidth
  val windowHeight: Double = ViewConfig.screenHeight

  def apply(listener: GameStartUpListener): MachiavelliStartUpStage = new MachiavelliStartUpPrimaryStageImpl(listener)

  def apply(): MachiavelliStartUpStage = new MachiavelliStartUpPrimaryStageImpl(null)
}

trait PrimaryStageListener extends StartUpSceneListener
