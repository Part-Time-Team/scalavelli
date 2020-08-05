package it.parttimeteam.Prolog

import java.io.InputStream
import alice.tuprolog.{Prolog, SolveInfo, Struct, Term, Theory, Var}

trait PrologEngine {

  /**
   * Resolve a specific goal
   *
   * @param predicate predicate to solve
   * @return goal result
   */
  def goal(predicate: Term): Set[Term]

  /**
   * Check if there are open alternatives.
   *
   * @return true if there are other solutions otherwise false
   */
  def hasOpenAlternatives: Boolean

  /**
   * Get all solution a specific goal
   *
   * @return list of terms with respect to the specific goal
   */
  def goals(goal: Term): List[Term]

  /**
   * Get value by term and variable
   *
   * @param info solution to get value
   * @return list of values
   */
  def bindingVars(info: SolveInfo): List[Term]

}

object PrologEngine {

  def apply(): PrologEngine = new PrologEngineImpl()

  /**
   * Load theory from specific file prolog
   *
   * @param theory theory to load
   * @return PrologEngine
   */
  def loadTheory(theory: InputStream): PrologEngine = {
    val prologEngine: PrologEngineImpl = new PrologEngineImpl()
    prologEngine.loadTheory(theory)
    prologEngine
  }

  private class PrologEngineImpl extends PrologEngine {

    val engine: Prolog = new Prolog()

    def loadTheory(stream: InputStream): Unit = engine.setTheory(new Theory(stream))

    override def goal(predicate: Term): Set[Term] = {
      val solveInfo: SolveInfo = engine.solve(predicate)
      isSuccess(solveInfo)
    }

    /**
     * Check if the result is successful
     *
     * @param solveInfo result of goal
     * @return
     */
    def isSuccess(solveInfo: SolveInfo): Set[Term] = {
      if (solveInfo isSuccess) {
        Set(solveInfo getSolution)
      } else {
        Set()
      }
    }

    override def hasOpenAlternatives: Boolean = engine.hasOpenAlternatives

    override def goals(goal: Term): List[Term] = {

      @scala.annotation.tailrec
      def _goals(info: SolveInfo)(solution: List[Term]): List[Term] = {
        if (info isSuccess) {
          val newSolutions = solution ++ bindingVars(info)
          if (engine hasOpenAlternatives) _goals(engine.solveNext())(newSolutions) else newSolutions
        } else solution
      }

      _goals(engine solve goal)(List())
    }

    override def bindingVars(info: SolveInfo): List[Term] = {

      var varList: List[Term] = List()
      info.getBindingVars.forEach(v => {
        varList = info.getTerm(v.getName) :: varList
      })
      varList
    }
  }

}

/**
 * Object Prolog Struct
 */
object PrologStruct {
  def apply(rule: String, variable: Var): Struct = new Struct(rule, variable)

  def apply(rule: String, variable: Term*): Struct = new Struct(rule, variable toArray)

  def apply(rule: String, parameters: Array[Term]): Struct = new Struct(rule, parameters)
}
