package it.parttimeteam.model.game

/**
 * Trait representing the history of a value T
 */
trait History[T] {
  def printAll(): Unit

  /**
   * Return if the history has a next value
   *
   * @return if the history has a next value
   */
  def canNext: Boolean

  /**
   * Return if the history has a previous value
   *
   * @return if the history has a previous value
   */
  def canPrevious: Boolean

  /**
   * Return if the history has a value
   *
   * @return if the history has a value
   */
  def isEmpty: Boolean

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

  /**
   * Clear all the history, removing all values
   *
   * @return
   */
  def clear(): History[T]

  /**
   * Reset to the beginning of the history, removing all next values
   *
   * @return the initial value if present and the updated history
   */
  def reset(): (Option[T], History[T])

  /**
   * Optionally selects the first element.
   *
   * @return First element.
   */
  def headOption: Option[T]

}

object History {
  def apply[T](): History[T] = new HistoryImpl[T]()

  private class HistoryImpl[T](private val values: Seq[T] = List.empty, private val index: Int = -1) extends History[T] {

    /**
     * @inheritdoc
     */
    override def getPresent: Option[T] = if (values.size >= index) values.lift(index) else None

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

    /**
     * @inheritdoc
     */
    override def clear(): History[T] = new HistoryImpl[T]()

    /**
     * @inheritdoc
     */
    override def reset(): (Option[T], History[T]) =
      if (values.nonEmpty) {
        (Some(values.head), new HistoryImpl[T](values.take(1), 0))
      } else
        (None, new HistoryImpl[T]())

    /**
     * @inheritdoc
     */
    override def isEmpty: Boolean = values.isEmpty

    /**
     * @inheritdoc
     */
    override def canNext: Boolean = {
      values.lift(index + 1) match {
        case Some(_) => true
        case None => false
      }
    }

    /**
     * @inheritdoc
     */
    override def canPrevious: Boolean = {
      values.lift(index - 1) match {
        case Some(_) => true
        case None => false
      }
    }

    override def printAll(): Unit = println(s"History: ${values.toString()}. Current is: $index")

    /**
     * @inheritdoc
     */
    override def headOption: Option[T] = values.headOption
  }

}

