package it.parttimeteam.core.prolog

import it.parttimeteam.core.cards.Color.{Blue, Red}
import it.parttimeteam.core.cards.{Color, Rank, Suit}
import it.parttimeteam.core.cards.Rank.{Ace, Eight, Five, Four, Jack, King, Nine, Queen, Seven, Six, Ten, Three, Two}
import it.parttimeteam.core.cards.Suit.{Clubs, Diamonds, Hearts, Spades}
import org.scalatest.prop.{TableDrivenPropertyChecks, TableFor1}
import org.scalatest.propspec.AnyPropSpec

class PrologGamePropSpec extends AnyPropSpec with TableDrivenPropertyChecks {

  val prologGame = new PrologGame

  val rank: TableFor1[Rank] = Table(
    "rank",
    Ace(),
    Two(),
    Three(),
    Four(),
    Five(),
    Six(),
    Seven(),
    Eight(),
    Nine(),
    Ten(),
    Jack(),
    Queen(),
    King()
  )

  val suit: TableFor1[Suit] = Table(
    "suit",
    Clubs(),
    Hearts(),
    Diamonds(),
    Spades()
  )

  val color: TableFor1[Color] = Table(
    "color",
    Blue(),
    Red()
  )

  property("loadDeck must return any possible card in the deck") {
    forAll(rank) { rank =>
      forAll(suit) {
        suit =>
          forAll(color) {
            color =>
              rank.shortName+suit.shortName+color.shortName contains prologGame.loadDeck
          }
      }
    }
  }
}
