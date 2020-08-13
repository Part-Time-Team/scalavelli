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

object History {
  def apply[T](): History[T] = new HistoryImpl[T]()
}

private class HistoryImpl[T](private val values: Seq[T] = List.empty, private val index: Int = -1) extends History[T] {

  /**
   * @inheritdoc
   */
  override def getPresent: Option[T] = if (this.values.size >= index) values.lift(index) else None

  /**
   * @inheritdoc
   */
  override def setPresent(value: T): History[T] = new HistoryImpl(values.take(index + 1) :+ value, index + 1)

  /**
   * @inheritdoc
   */
  override def previous(): (Option[T], History[T]) = values.lift(index - 1) match {
    case s@Some(_) => (s, new HistoryImpl(values, index - 1))
    case n@None => (n, this)
  }

  /**
   * @inheritdoc
   */
  override def next(): (Option[T], History[T]) = values.lift(index + 1) match {
    case s@Some(_) => (s, new HistoryImpl(values, index + 1))
    case n@None => (n, this)
  }

}

object Seq {

}
