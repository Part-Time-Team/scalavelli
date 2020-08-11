package it.parttimeteam.Prolog.engine

import java.io.InputStream
import alice.tuprolog._
import it.parttimeteam.Prolog.engine.PrologEngine.theory
import scala.language.postfixOps

/**
 * Class to improve TuPrologQuality
 */
class PrologGameEngine extends PrologEngine {

  val engine: Prolog = new Prolog()
  loadTheory(theory)

  /**
   * Load theory from specific file prolog
   *
   * @param theory theory to load
   * @return PrologEngine
   */
  private def loadTheory(theory: InputStream): Unit = {
    engine.setTheory(new Theory(theory))
  }

  /**
   * Check if the result is successful
   *
   * @param solveInfo result of goal
   * @return
   */
  private def isSuccess(solveInfo: SolveInfo): Set[Term] = {
    if (solveInfo isSuccess) {
      Set(solveInfo getSolution)
    } else {
      Set()
    }
  }

  override def goal(predicate: Term): Set[Term] = {
    val solveInfo: SolveInfo = engine.solve(predicate)
    isSuccess(solveInfo)
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

/**
 * Object Prolog Struct to execute the goals
 */
object PrologStruct {

  def apply(rule: String, variable: Var): Struct = new Struct(rule, variable)
  def apply(rule: String, variable: Term*): Struct = new Struct(rule, variable toArray)
  def apply(rule: String, parameters: Array[Term]): Struct = new Struct(rule, parameters)
}
