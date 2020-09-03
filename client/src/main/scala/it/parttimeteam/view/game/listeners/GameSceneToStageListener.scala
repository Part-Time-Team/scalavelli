package it.parttimeteam.view.game.listeners

trait GameSceneToStageListener {
  def clearHandSelection(): Unit

  def pickCombination(combinationId: String)

  def makeCombination()

  def pickCards()

  def sortHandBySuit()

  def sortHandByRank()

  def endTurn()

  def nextState()

  def previousState()
}
