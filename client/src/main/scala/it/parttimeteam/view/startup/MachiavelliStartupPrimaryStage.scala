package it.parttimeteam.view.startup

import it.parttimeteam.controller.startup.StartupStageToControllerListener
import it.parttimeteam.view.SelectSceneListener
import it.parttimeteam.view.startup.listeners.{CreatePrivateGameSceneListener, GameWithPlayersSceneListener, PrivateGameSceneListener}
import it.parttimeteam.view.startup.scenes.{CreatePrivateGameScene, GameWithPlayersScene, PrivateGameScene, SelectScene}
import scalafx.application.JFXApp

trait MachiavelliStartupPrimaryStage extends JFXApp.PrimaryStage with PrimaryStageListener

/** Companion object */
object MachiavelliStartupPrimaryStage {

  def apply(listener: StartupStageToControllerListener,
            windowWidth: Double = 800d,
            windowHeight: Double = 600d): MachiavelliStartupPrimaryStage = new MachiavelliStartupPrimaryStageImpl(listener, windowWidth, windowHeight)


  class MachiavelliStartupPrimaryStageImpl(private val toControllerListener: StartupStageToControllerListener, private val windowWidth: Double, private val windowHeight: Double) extends MachiavelliStartupPrimaryStage {
    title = "Machiavelli"
    resizable = true
    width = windowWidth
    height = windowHeight

    val stage: MachiavelliStartupPrimaryStage = this

    private val mainScene = new SelectScene(this, new SelectSceneListener {

      override def onSelectedGameWithPlayers(): Unit = scene = new GameWithPlayersScene(stage, stage)

      override def onSelectedGameWithCode(): Unit = scene = new PrivateGameScene(stage, stage)

      override def onSelectedCreatePrivateGame(): Unit = scene = new CreatePrivateGameScene(stage, stage)
    })

    scene = mainScene

    onCloseRequest = _ => {
      System.exit(0)
    }

    override def registerToPrivateGame(username: String, privateCode: String): Unit = toControllerListener.requestPrivateGame()

    override def registerToGame(username: String, playersNumber: Int): Unit = toControllerListener.requestGameWithPlayers()

    override def createPrivateGame(username: String, playersNumber: Int): Unit = toControllerListener.createPrivateGame()
  }

}

trait PrimaryStageListener extends GameWithPlayersSceneListener with PrivateGameSceneListener with CreatePrivateGameSceneListener
