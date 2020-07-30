package it.parttimeteam.Prolog

import java.io.InputStream

import org.apache.commons.lang3.tuple.Pair

trait PrologEngine {

  /**
   * Resolve a specific goal
   *
   * @param predicate
   * @return goal result
   */
  def goal(predicate: Term): Set[Term]

  /**
   * Check if there are open alternatives.
   *
   * @return
   */
  def hasOpenAlternatives: Boolean

  /**
   * Get all solution a specific goal
   *
   * @return
   */
  def goals(goal: Term): List[Term]

}

object PrologEngine {

  //Apply
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
  }

  // TODO finire il binding var per capire quanti variabili sono nel predicato
  def bindingVars(info: SolveInfo): List[Term] = {

    val list = List()
    info.getBindingVars.forEach( v => v.getName :: list)
    println(list)
    list
  }
}

object PrologStruct {
  def apply(rule: String, variable: Var): Struct = new Struct(rule, variable)

  def apply(rule: String, variable: Term*): Struct = new Struct(rule, variable toArray)

  def apply(rule: String, parameters: Array[Term]): Struct = new Struct(rule, parameters)
}
