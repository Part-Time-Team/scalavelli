package it.parttimeteam.view.utils

import it.parttimeteam.core.cards.Card
import it.parttimeteam.core.cards.Rank._
import it.parttimeteam.core.cards.Suit._

object CardUtils {

  /**
    * Return the card image path
    *
    * @param card the card which image should be loaded
    * @return the card image path
    */
  def getCardPath(card: Card): String = {
    val path: String = ImagePaths.CARDS_BASE_PATH
    val suitPath = card.suit match {
      case Hearts() => "H"
      case Diamonds() => "D"
      case Clubs() => "C"
      case Spades() => "S"
      case _ =>
    }

    val rankPath = card.rank match {
      case Ace() | OverflowAce() => "1"
      case Two() => "2"
      case Three() => "3"
      case Four() => "4"
      case Five() => "5"
      case Six() => "6"
      case Seven() => "7"
      case Eight() => "8"
      case Nine() => "9"
      case Ten() => "10"
      case Jack() => "11"
      case Queen() => "12"
      case King() => "13"
      case _ =>
    }

    path + suitPath + rankPath + ".png"
  }

}
