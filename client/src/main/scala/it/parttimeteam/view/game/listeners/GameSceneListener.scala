package it.parttimeteam.view.game.listeners

/**
  * Action which can be made by GameScene
  */
trait GameSceneListener {
  def clearHandSelection(): Unit

  /**
    * The player pick a combination from the game board.
    *
    * @param combinationId the CardCombination identifier picked by player
    */
  def pickCombination(combinationId: String)

  /**
    * The player clicked the make combination button.
    */
  def makeCombination()

  /**
    * The player clicked the pick cards button.
    */
  def pickCards()

  /**
    * The player clicked the update combination button.
    */
  def updateCombination()

  /**
    * The player sorts his hand cards by suit.
    */
  def sortHandBySuit()

  /**
    * The player sorts his hand cards by rank.
    */
  def sortHandByRank()

  /**
    * The player ends his turn.
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
}
