package it.parttimeteam.view.game

import it.parttimeteam.controller.ViewMessage
import it.parttimeteam.controller.game.GameListener
import it.parttimeteam.model.ErrorEvent
import it.parttimeteam.model.game.ClientGameState
import it.parttimeteam.view._

/**
  * Main stage for the game view, interacts with GameScene
  */
trait GameStage extends BaseStage {
  /**
    * Set the current user turn
    *
    * @return
    */
  def setInTurn(): Unit

  /**
    * Set the current user turn ended
    *
    * @return
    */
  def setTurnEnded(): Unit

  /**
    * Make the timer countdown visible.
    *
    * @param minutes countdown minutes
    * @param seconds countdown seconds
    */
  def showTimer(minutes: Long, seconds: Long): Unit

  /**
    * Update countdown.
    *
    * @param minutes countdown minutes
    * @param seconds countdown seconds
    */
  def updateTimer(minutes: Long, seconds: Long): Unit

  /**
    * End countdown and show alert
    */
  def notifyTimerEnded(): Unit

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
  def setMessage(message: ViewMessage): Unit

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
    * @param gameState the actual game state
    */
  def updateState(gameState: ClientGameState): Unit

  /**
    * Notify the player an error.
    *
    * @param error the error occurred
    */
  def notifyError(error: ErrorEvent): Unit
}

/**
  * Companion object for ScalavelliGameStage
  */
object GameStage {

  def apply(listener: GameListener): GameStage = new GameStageImpl(listener)
}