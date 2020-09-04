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
  def pickCombination(combinationId: String): Unit

  /**
    * The player clicked the make combination button.
    */
  def makeCombination(): Unit

  /**
    * The player clicked the pick cards button.
    */
  def pickCards(): Unit

  /**
    * The player clicked the update combination button.
    */
  def updateCombination(): Unit

  /**
    * The player sorts his hand cards by suit.
    */
  def sortHandBySuit(): Unit

  /**
    * The player sorts his hand cards by rank.
    */
  def sortHandByRank(): Unit

  /**
    * The player ends his turn.
    */
  def endTurn(): Unit

  /**
    * The player goes forward in turn history.
    */
  def nextState(): Unit

  /**
    * The player goes backward in turn history.
    */
  def previousState(): Unit

  /**
    * The player goes back to initial turn state.
    */
  def resetState(): Unit

}
