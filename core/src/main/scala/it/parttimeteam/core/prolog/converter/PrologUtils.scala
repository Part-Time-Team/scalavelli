package it.parttimeteam.core.prolog.converter

import alice.tuprolog.Term
import scala.util.matching.Regex

object PrologUtils {

  val pattern: Regex = "[0-9]+,[a-zA-Z]+".r

  def utils(cards: Seq[Term]): List[Regex.Match] = {

    val cardsString: String = replaceString(replaceSeq(cards, "','"))
    pattern findAllMatchIn substring(cardsString, 1, cardsString.length - 1) toList
  }

  private def replaceSeq(term: Seq[Term], replace: String): String = term.toString.replace(replace, "")

  private def replaceString(stringToReplace: String): String = {
    stringToReplace.replace("List", "").replace("[", "")
      .replace("]", "").replace(" ", "").replace("'", "")
  }

  private def substring(cardsString: String, start: Int, end: Int): String = {
    cardsString.substring(start, end)
  }
}
