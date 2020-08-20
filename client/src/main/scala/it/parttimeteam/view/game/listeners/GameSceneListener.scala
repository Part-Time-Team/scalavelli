package it.parttimeteam.view.game.listeners

import it.parttimeteam.core.cards.Card

trait GameSceneListener {
  def pickCombination(combinationIndex: Int)

  def makeCombination(cards: Seq[Card])

  def endTurn()

  def nextState()

  def previousState()
}
