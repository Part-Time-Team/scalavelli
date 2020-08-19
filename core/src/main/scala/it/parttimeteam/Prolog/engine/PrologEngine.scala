package it.parttimeteam.Prolog.engine

import java.io.InputStream
import alice.tuprolog.{SolveInfo, Term}

/**
 * Helper facilities to improve TuProlog quality.
 */
trait PrologEngine {

  /**
   * Resolve a specific goal
   *
   * @param predicate predicate (Term) to solve
   * @return goal result
   */
  def goal(predicate: Term): List[Term]

  /**
   * Resolve a specific goal
   *
   * @param predicate predicate (String) to solve
   * @return goal result
   */
  def goal(predicate: String): List[Term]

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

}

/**
 * Object to initialize the class PrologGameEngine
 */
object PrologEngine {
  val theory: InputStream = getClass.getResourceAsStream("rules.prolog")

  def apply(): PrologGameEngine = new PrologGameEngine()
}
