package it.parttimeteam.core.prolog.engine

import alice.tuprolog._
import scala.language.postfixOps

/**
 * Class to improve TuPrologQuality
 */
class PrologGameEngine() extends PrologEngine {

  val engine: Prolog = new Prolog()

  /**
   * Set theory from specific file prolog
   */
  engine.setTheory(new Theory(getClass.getResourceAsStream("/rules.prolog")))

  override def goal(predicate: Term): Seq[Term] = {
    val info: SolveInfo = engine solve predicate
    if (info isSuccess) bindingVars(info)
    else Seq()
  }

  override def goal(predicate: String): Seq[Term] = {
    val info: SolveInfo = engine solve predicate
    if (info isSuccess) bindingVars(info)
    else Seq()
  }

  override def hasOpenAlternatives: Boolean = engine hasOpenAlternatives

  override def goals(goal: Term): Seq[Term] = {

    @scala.annotation.tailrec
    def _goals(info: SolveInfo)(solution: Seq[Term]): Seq[Term] = {
      if (info isSuccess) {
        val newSolutions = solution ++ bindingVars(info)
        if (engine hasOpenAlternatives) _goals(engine solveNext)(newSolutions) else newSolutions
      } else solution
    }

    _goals(engine solve goal)(Seq())
  }

  override def bindingVars(info: SolveInfo): Seq[Term] = {

    var varList: Seq[Term] = Seq()
    info.getBindingVars.forEach(v => {
      varList = info.getTerm(v.getName) +: varList
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
