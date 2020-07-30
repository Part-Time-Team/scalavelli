package it.parttimeteam.view.startup

import it.parttimeteam.controller.startup.StartupStageToControllerListener
import it.parttimeteam.view.startup.listeners.{CreatePrivateGameSceneListener, GameWithPlayersSceneListener, PrivateGameSceneListener, SelectSceneListener}
import it.parttimeteam.view.startup.scenes.{CreatePrivateGameScene, GameWithPlayersScene, PrivateGameScene, SelectScene}
import scalafx.application.JFXApp

case class MachiavelliStartupPrimaryStage(toControllerListener: StartupStageToControllerListener, windowWidth: Double, windowHeight: Double) extends JFXApp.PrimaryStage with PrimaryStageListener {
  title = "Machiavelli"
  resizable = true
  width = windowWidth
  height = windowHeight

  val stage: MachiavelliStartupPrimaryStage = this


  private val mainScene = new SelectScene(this, new SelectSceneListener {

    override def onSelectedGameWithPlayers(): Unit = scene = new GameWithPlayersScene(stage, stage, onBackClick)

    override def onSelectedGameWithCode(): Unit = scene = new PrivateGameScene(stage, stage, onBackClick)

    override def onSelectedCreatePrivateGame(): Unit = scene = new CreatePrivateGameScene(stage, stage, onBackClick)
  })

  def onBackClick() {
    scene = mainScene
  }

  scene = mainScene

  onCloseRequest = _ => {
    System.exit(0)
  }

  override def registerToPrivateGame(username: String, privateCode: String): Unit = toControllerListener.requestPrivateGame()

  override def registerToGame(username: String, playersNumber: Int): Unit = toControllerListener.requestGameWithPlayers(username, playersNumber)

  override def createPrivateGame(username: String, playersNumber: Int): Unit = toControllerListener.createPrivateGame()
}

object MachiavelliStartupPrimaryStage {
  val windowWidth: Double = 800d
  val windowHeight: Double = 600d

  def apply(listener: StartupStageToControllerListener): MachiavelliStartupPrimaryStage = MachiavelliStartupPrimaryStage(listener, windowWidth, windowHeight)


  def apply(): MachiavelliStartupPrimaryStage = MachiavelliStartupPrimaryStage(null, windowWidth, windowHeight)

}

trait PrimaryStageListener extends GameWithPlayersSceneListener with PrivateGameSceneListener with CreatePrivateGameSceneListener
