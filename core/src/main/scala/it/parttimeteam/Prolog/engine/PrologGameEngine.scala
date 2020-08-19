package it.parttimeteam.Prolog.engine

import alice.tuprolog._
import it.parttimeteam.Prolog.engine.PrologEngine.theory

import scala.language.postfixOps

/**
 * Class to improve TuPrologQuality
 */
class PrologGameEngine() extends PrologEngine {

  val engine: Prolog = new Prolog()

  /**
   * Set theory from specific file prolog
   */
  engine.setTheory(new Theory(theory))

  override def goal(predicate: Term): List[Term] = {
    val info: SolveInfo = engine solve predicate
    if (info isSuccess) bindingVars(info)
    else List()
  }

  override def goal(predicate: String): List[Term] = {
    val info: SolveInfo = engine solve predicate
    if (info isSuccess) bindingVars(info)
    else List()
  }

  override def hasOpenAlternatives: Boolean = engine hasOpenAlternatives

  override def goals(goal: Term): List[Term] = {

    @scala.annotation.tailrec
    def _goals(info: SolveInfo)(solution: List[Term]): List[Term] = {
      if (info isSuccess) {
        val newSolutions = solution ++ bindingVars(info)
        if (engine hasOpenAlternatives) _goals(engine solveNext)(newSolutions) else newSolutions
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
