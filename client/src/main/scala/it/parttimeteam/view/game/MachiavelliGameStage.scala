package it.parttimeteam.view.game

import it.parttimeteam.Constants
import it.parttimeteam.controller.game.GameListener
import it.parttimeteam.core.cards.Card
import it.parttimeteam.gamestate.PlayerGameState
import it.parttimeteam.view._
import it.parttimeteam.view.game.listeners.GameStageListener
import it.parttimeteam.view.game.scenes.GameScene
import it.parttimeteam.view.game.scenes.GameScene.GameSceneImpl
import it.parttimeteam.view.utils.MachiavelliAlert
import scalafx.application.{JFXApp, Platform}
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, ButtonType}

/**
  * Main stage for the game view, interacts with GameScene
  */
trait MachiavelliGameStage extends JFXApp.PrimaryStage with GameStageListener {
  /**
    * Make the timer countdown visible.
    */
  def showTimer(): Unit

  /**
    * Hide the timer countdown.
    */
  def hideTimer(): Unit

  /**
    * Enable player actions on view.
    */
  def enableActions(): Unit

  /**
    * Disabled player actions on view.
    */
  def disableActions(): Unit

  /**
    * Notify the player the game ended.
    *
    * @param gameEndType the type of game end
    */
  def notifyGameEnd(gameEndType: GameEndType): Unit


  /**
    * Notify the player an info message.
    *
    * @param message the message to be displayed
    */
  def notifyInfo(message: String): Unit

  /**
    * Display a message in the RightBar.
    *
    * @param message the message to be displayed
    */
  def setMessage(message: String): Unit

  /**
    * Called when the match is ready.
    */
  def matchReady(): Unit

  /**
    * Called when the match start setting up.
    */
  def initMatch(): Unit

  /**
    * Updates the displayed game state with the actual one.
    *
    * @param playerGameState the actual game state
    */
  def updateState(playerGameState: PlayerGameState): Unit

  /**
    * Notify the player an error.
    *
    * @param error the error occurred
    */
  def notifyError(error: String): Unit

  /**
    * Set history possible actions
    * @param canUndo if the undo action is available
    * @param canRedo if the redo action is available
    */
  def updateHistoryState(canUndo: Boolean, canRedo: Boolean): Unit
}

/**
  * Companion object for MachiavelliGameStage
  */
object MachiavelliGameStage {
  val windowWidth: Double = ViewConfig.screenWidth
  val windowHeight: Double = ViewConfig.screenHeight

  def apply(listener: GameListener): MachiavelliGameStage = new MachiavelliGameStageImpl(listener, windowWidth, windowHeight)

  class MachiavelliGameStageImpl(gameListener: GameListener, windowWidth: Double, windowHeight: Double) extends MachiavelliGameStage {
    title = Constants.Client.GAME_NAME
    resizable = true
    width = windowWidth
    height = windowHeight

    val stage: MachiavelliGameStage = this

    val gameScene: GameScene = new GameSceneImpl(stage)

    scene = gameScene

    onCloseRequest = e => {
      val alert: Alert = MachiavelliAlert("Leave the game", "Are you sure you want to leave the game? The action cannot be undone.", AlertType.Confirmation)

      alert.showAndWait match {
        case Some(b) =>
          if (b == ButtonType.OK) {
            gameListener.onViewEvent(LeaveGameEvent)
            System.exit(0)
          }

        case None =>
      }
      e.consume()
    }

    /** @inheritdoc */
    override def showTimer(): Unit = {
      Platform.runLater({
        gameScene.showTimer()
      })
    }

    /** @inheritdoc */
    override def setMessage(message: String): Unit = {
      Platform.runLater({
        gameScene.setMessage(message)
      })
    }

    /** @inheritdoc */
    override def updateState(state: PlayerGameState): Unit = {
      Platform.runLater({
        gameScene.setState(state)
      })
    }

    /** @inheritdoc */
    override def updateHistoryState(canUndo: Boolean, canRedo: Boolean): Unit = {
      gameScene.setHistoryState(canUndo, canRedo)
    }

    /** @inheritdoc */
    override def initMatch(): Unit = {
      Platform.runLater({
        gameScene.showInitMatch()
      })
    }

    /** @inheritdoc */
    override def matchReady(): Unit = {
      Platform.runLater({
        gameScene.hideInitMatch()
      })
    }

    /** @inheritdoc */
    override def notifyGameEnd(gameEndType: GameEndType): Unit = {
      Platform.runLater({
        gameScene.gameEnded(gameEndType)
      })
    }

    /** @inheritdoc */
    override def enableActions(): Unit = {
      Platform.runLater({
        gameScene.enableActions()
      })
    }

    /** @inheritdoc */
    override def disableActions(): Unit = {
      Platform.runLater({
        gameScene.disableActions()
      })
    }

    /** @inheritdoc */
    override def hideTimer(): Unit = {
      Platform.runLater({
        gameScene.hideTimer()
      })
    }

    /** @inheritdoc */
    override def notifyError(result: String): Unit = {
      Platform.runLater({
        val alert: Alert = MachiavelliAlert("Error", result, AlertType.Error)
        alert.showAndWait()
      })
    }

    /** @inheritdoc */
    override def notifyInfo(message: String): Unit = {
      Platform.runLater({
        val alert: Alert = MachiavelliAlert("", message, AlertType.Information)
        alert.showAndWait()
      })
    }


    // view actions
    /** @inheritdoc*/
    override def pickCombination(combinationId: String): Unit = gameListener.onViewEvent(PickCardCombinationEvent(combinationId))

    /** @inheritdoc*/
    override def endTurn(): Unit = gameListener.onViewEvent(EndTurnEvent)

    /** @inheritdoc*/
    override def nextState(): Unit = gameListener.onViewEvent(RedoEvent)

    /** @inheritdoc*/
    override def previousState(): Unit = gameListener.onViewEvent(UndoEvent)

    /** @inheritdoc*/
    override def makeCombination(cards: Seq[Card]): Unit = gameListener.onViewEvent(MakeCombinationEvent(cards))

    /** @inheritdoc*/
    override def pickCards(cards: Seq[Card]): Unit = gameListener.onViewEvent(PickCardsEvent(cards))

    /** @inheritdoc*/
    override def sortHandBySuit(): Unit = gameListener.onViewEvent(SortHandBySuitEvent)

    /** @inheritdoc*/
    override def sortHandByRank(): Unit = gameListener.onViewEvent(SortHandByRankEvent)

    /** @inheritdoc*/
    override def updateCardCombination(combinationId: String, cards: Seq[Card]): Unit = gameListener.onViewEvent(UpdateCardCombinationEvent(combinationId, cards))

    /** @inheritdoc*/
    override def leaveGame(): Unit = gameListener.onViewEvent(LeaveGameEvent)

    /** @inheritdoc*/
    override def resetHistory(): Unit = gameListener.onViewEvent(ResetEvent)
  }

}