package it.parttimeteam.view.game.listeners

import it.parttimeteam.core.cards.Card

trait GameStageToControllerListener {
  
  def pickCombination(combinationId: String)

  def makeCombination(cards: Seq[Card])

  def updateCardCombination(combinationId: String, cards: Seq[Card])

  def pickCards(cards: Seq[Card])
  
  def sortHandBySuit()
  
  def sortHandByRank()

  def endTurn()

  def endTurnAndDraw()

  def nextState()

  def previousState()

  def leaveGame()

  def resetHistory()
}
