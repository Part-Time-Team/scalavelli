package it.parttimeteam.`match`

trait TurnManager[T] {

  /**
   *
   * @return the id of the current player in turn
   */
  def getInTurn: T

  /**
   *
   * Go to next turn
   *
   * @return the next player id
   */
  def nextTurn: T

}

object TurnManager {

  def apply[T](ids: Seq[T]): TurnManager[T] = new TurnManagerImpl(ids)

  private class TurnManagerImpl[T](ids: Seq[T]) extends TurnManager[T] {

    private var currentTurnIndex = 0

    /**
     *
     * @return the id of the current player in turn
     */
    override def getInTurn: T = ids(currentTurnIndex)

    /**
     *
     * Go to next turn
     *
     * @return the next player id
     */
    override def nextTurn: T = {
      currentTurnIndex = (currentTurnIndex + 1) % ids.size
      getInTurn
    }
  }

}


