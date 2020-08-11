package it.parttimeteam.Prolog.engine

import alice.tuprolog.{SolveInfo, Term}

/**
 * Helper facilities to improve TuProlog quality.
 */
trait PrologEngine {

  /**
   * Resolve a specific goal
   *
   * @param predicate predicate to solve
   * @return goal result
   */
  def goal(predicate: Term): Set[Term]

  /**
   * Get all solution a specific goal
   *
   * @return list of terms with respect to the specific goal
   */
  def goals(goal: Term): List[Term]

  /**
   * Check if there are open alternatives.
   *
   * @return true if there are other solutions otherwise false
   */
  def hasOpenAlternatives: Boolean

  /**
   * Get value by term and variable
   *
   * @param info solution to get value
   * @return list of values
   */
  def bindingVars(info: SolveInfo): List[Term]

  /**
   * Convert term to int
   *
   * @param term term to covert
   * @return type int
   */
  def toInt(term: Term): Int

  def toTerm(stringTerm: String): Term
}

/**
 * Object PrologEngine to initialize the class PrologGameEngine
 */
object PrologEngine {

  def apply(): PrologGameEngine = new PrologGameEngine
}
