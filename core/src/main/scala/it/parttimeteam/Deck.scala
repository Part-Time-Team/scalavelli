package it.parttimeteam

import scala.util.Random

case class Deck(var cards: List[Card]){
  def draw(): Card = {
    val card = cards.head
    cards = cards.tail
    card
  }

  def remaining: Int = cards.length

  def isEmpty: Boolean = cards.isEmpty

  override def toString: String = cards.mkString(", ")
}

object Deck {
  def sorted: Deck = Deck(Card.fullDeck)

  def shuffled: Deck = Deck(Random.shuffle(Card.fullDeck))

  def empty: Deck = Deck(List[Card]())

  implicit def cards2deck(cards: List[Card]): Deck = Deck(cards)
}
