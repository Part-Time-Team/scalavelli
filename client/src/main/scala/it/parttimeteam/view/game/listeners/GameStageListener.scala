package it.parttimeteam.view.game.listeners

import it.parttimeteam.core.cards.Card

/**
  * Actions which can be made by MachiavelliGameStage
  */
trait GameStageListener {
  /**
    * The player pick a combination from the game board.
    *
    * @param combinationId the CardCombination identifier picked by player
    */
  def pickCombination(combinationId: String)

  /**
    * The player plays a combination of card from his hand.
    *
    * @param cards the sequence of played cards
    */
  def makeCombination(cards: Seq[Card])

  /**
    * The player updates an existing CardCombination with some cards from his hand.
    *
    * @param combinationId the CardCombination identifier picked by player
    * @param cards         the sequence of played cards
    */
  def updateCardCombination(combinationId: String, cards: Seq[Card])

  /**
    * The player picks some cards from the game board.
    *
    * @param cards the sequence of picked cards
    */
  def pickCards(cards: Seq[Card])

  /**
    * The player sorts his hand cards by suit.
    */
  def sortHandBySuit()

  /**
    * The player sorts his hand cards by rank.
    */
  def sortHandByRank()

  /**
    * The player ends his turn without drawing.
    */
  def endTurn()

  /**
    * The player goes forward in turn history.
    */
  def nextState()

  /**
    * The player goes backward in turn history.
    */
  def previousState()

  /**
    * The player leave the game.
    */
  def leaveGame()

  /**
    * The player goes to the initial state of his turn.
    */
  def resetHistory()
}
