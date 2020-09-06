package it.parttimeteam.view.game.scenes

import it.parttimeteam.model.game.ClientGameState
import it.parttimeteam.view.game.scenes.model.{GameCard, GameCardCombination}
import scalafx.scene.Scene

/**
  * Main scene for MachiavelliGameStage.
  * Allows MachiavelliGameStage to interact with each view element.
  */
trait GameScene extends Scene {

  /**
    * Allows to show timer during the player turn.
    */
  def showTimer(): Unit

  /**
    * Allows to hide timer after the player turn.
    */
  def hideTimer(): Unit

  /**
    * Enable all the view actions during the player turn.
    */
  def enableActions(): Unit

  /**
    * Disable all the view actions when the player turn is ended.
    */
  def disableActions(): Unit

  /**
    * Show a blocking dialog while the game is set up.
    */
  def showInitMatch(): Unit

  /**
    * Hide the blocking dialog when the game is set up.
    */
  def hideInitMatch(): Unit

  /**
    * Sets the actual ViewGameState inside view.
    *
    * @param clientGameState the actual ViewGameState
    */
  def setState(clientGameState: ClientGameState): Unit

  /**
    * Display a message in the RightBar.
    *
    * @param message the message to be displayed
    */
  def setMessage(message: String): Unit

  /**
    * Set if is the player turn
    *
    * @param inTurn if is the player turn
    */
  def setInTurn(inTurn: Boolean): Unit
}

object GameScene {

  /**
    * Actions which should be invoked inside the game board.
    */
  trait BoardListener extends CardListener {
    /**
      * The player pick a combination from the game board.
      *
      * @param cardCombination the GameCardCombination picked by player
      */
    def onPickCombinationClick(cardCombination: GameCardCombination): Unit

    /**
      * The player click on a combination on the game board.
      *
      * @param cardCombination the GameCardCombination clicked by player
      */
    def onCombinationClicked(cardCombination: GameCardCombination): Unit
  }

  /**
    * Actions which should be invoked inside the player hand.
    */
  trait HandListener extends CardListener {

  }

  trait CardListener {
    /**
      * The player click a card.
      *
      * @param card the GameCard clicked by player
      */
    def onCardClicked(card: GameCard)
  }

}

