package it.parttimeteam

case class Card(val rank: Rank, val suit: Suit) {
  def name(): String = f"${rank.name} of ${suit.name}"
  def shortName(): String = f"${rank.shortName}${suit.shortName}"

  // TODO: This function is necessary? We need score calculation?
  def score(): Int = {
    (rank, suit) match {
      case (Rank.Two(), Suit.Clubs()) => 2
      case (Rank.Ten(), Suit.Diamonds()) => 3
      case (Rank.Jack(), _) => 1
      case (Rank.Ace(), _) => 1
      case _ => 0
    }
  }

  def canFish(other: Card) = {
    if(rank == Rank.Jack()) true
    else this == other
  }

  override def toString(): String = shortName()
}
