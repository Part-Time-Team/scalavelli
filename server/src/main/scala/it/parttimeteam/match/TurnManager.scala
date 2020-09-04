package it.parttimeteam.`match`

trait TurnManager[T] {

  /**
   *
   * @return the current participant in turn
   */
  def getInTurn: T

  /**
   *
   * Go to next turn
   *
   * @return the next participant
   */
  def nextTurn: T

}

object TurnManager {

  def apply[T](participants: Seq[T]): TurnManager[T] = new TurnManagerImpl(participants)

  private class TurnManagerImpl[T](participants: Seq[T]) extends TurnManager[T] {

    private var currentTurnIndex = 0

    /**
     * @inheritdoc
     */
    override def getInTurn: T = participants(currentTurnIndex)

    /**
     * @inheritdoc
     */
    override def nextTurn: T = {
      currentTurnIndex = (currentTurnIndex + 1) % participants.size
      getInTurn
    }
  }

}


