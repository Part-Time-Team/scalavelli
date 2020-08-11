package it.parttimeteam.model.game

/**
 * Trait representing the history of a value T
 */
trait History[T] {

  /**
   *
   * @return the present value
   */
  def getPresent: Option[T]

  /**
   * Sets the present value
   *
   * @param value value to add as present value
   * @return the updated history
   */
  def setPresent(value: T): History[T]

  /**
   * Move the history back
   *
   * @return the previous value and the updated history
   */
  def previous(): (Option[T], History[T])

  /**
   * Moves the history forward
   *
   * @return the next value and the updated history
   */
  def next(): (Option[T], History[T])

}
