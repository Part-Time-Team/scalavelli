package it.parttimeteam.core.prolog.converter

import alice.tuprolog.Term
import scala.util.matching.Regex

/**
 * object to clean the prolog term
 */
object PrologUtils {

  /**
   * String pattern
   */
  val pattern: Regex = "[0-9]+,\\([HCSD][RB]\\)".r

  /**
   * Clean the prolog term by converting into a List of regex
   *
   * @param cards cards to clean and convert
   * @return regex list
   */
  def utils(cards: Seq[Term]): Seq[Regex.Match] = {

    val cardsString: String = replace(cards)
    (pattern findAllMatchIn substring(cardsString, cardsString.length - 1)).toList
  }

  /**
   * Invoke replaceSeqToString and replaceString
   */
  private def replace(term: Seq[Term]): String = replaceString(replaceSeqToString(term))

  /**
   * Replace specific characters in a sequence of terms
   *
   * @param term sequence of terms
   * @return converted string
   */
  private def replaceSeqToString(term: Seq[Term]): String = term.toString.replace("','", "")

  /**
   * Replace specific characters in a terms
   *
   * @param term    term to clean
   * @param replace specific characters to replace
   * @return converted string
   */
  def replaceTermToString(term: Term, replace: String): String = term.toString.replace(replace, "")

  // TODO da testare, add scalaDoc
  def splitRankSuitColor(rankSuitColor: Array[String]): (String, String, String) = {

    val rank: String = rankSuitColor(0)
    val suitColor: (String, String) = (rankSuitColor(1).replace("(", "").replace(")", "") slice(0, 1),
      rankSuitColor(1).replace("(", "").replace(")", "") slice(1, 2))
    (rank, suitColor._1, suitColor._2)
  }

  /**
   * Replace specific characters in a string
   *
   * @param stringToReplace string to clean
   * @return converted string
   */
  private def replaceString(stringToReplace: String): String = {
    stringToReplace.replace("List", "").replace("[", "")
      .replace("]", "").replace(" ", "").replace("'", "")
  }

  /**
   * Determines a substring
   *
   * @param cardsString starting string
   * @param endString   termination character
   * @return substring
   */
  private def substring(cardsString: String, endString: Int): String = {
    cardsString.substring(1, endString)
  }
}
