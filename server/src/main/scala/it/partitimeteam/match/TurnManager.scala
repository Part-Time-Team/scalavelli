package it.partitimeteam.`match`

trait TurnManager {

  /**
   *
   * @return the id of the current player in turn
   */
  def playerInTurnId: String

  /**
   *
   * Go to next turn
   *
   * @return the next player id
   */
  def nextTurn: String

}

object TurnManager {

  def apply(ids: Seq[String]): TurnManager = new TurnManagerImpl(ids)

  private class TurnManagerImpl(ids: Seq[String]) extends TurnManager {

    private var currentTurnIndex = 0

    /**
     *
     * @return the id of the current player in turn
     */
    override def playerInTurnId: String = ids(currentTurnIndex)

    /**
     *
     * Go to next turn
     *
     * @return the next player id
     */
    override def nextTurn: String = {
      currentTurnIndex = (currentTurnIndex + 1) % ids.size
      playerInTurnId
    }
  }

}


