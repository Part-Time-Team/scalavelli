package it.parttimeteam.view.game.listeners

trait GameSceneListener {
  def pickCombination(combinationId: String)

  def makeCombination()

  def endTurn()

  def nextState()

  def previousState()
}
