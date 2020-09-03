package it.parttimeteam.view.game.listeners

import it.parttimeteam.core.cards.Card

trait GameSceneToStageListener {

  def pickCombination(combinationId: String)

  def makeCombination(cards: Card*)

  def pickCards(cards: Card*)

  def sortHandBySuit()

  def sortHandByRank()

  def endTurn()

  def nextState()

  def previousState()
}
