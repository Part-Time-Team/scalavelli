package it.parttimeteam.view.game.listeners

import it.parttimeteam.core.cards.Card

trait GameStageToControllerListener {
  
  def pickCombination(combinationId: String)

  def makeCombination(cards: Seq[Card])

  def updateCardCombination(combinationId: String, card: Card)

  def pickCards(cards: Seq[Card])
  
  def sortHandBySuit()
  
  def sortHandByRank()

  def endTurn()

  def nextState()

  def previousState()
}
