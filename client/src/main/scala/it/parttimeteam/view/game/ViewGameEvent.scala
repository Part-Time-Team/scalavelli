package it.parttimeteam.view.game

import it.parttimeteam.core.cards.Card

/**
  * Possible game view events which can be triggered by player.
  */
sealed class ViewGameEvent

/**
  * The player leaves the game.
  */
case object LeaveGameEvent extends ViewGameEvent

/**
  * The player goes backward in turn history.
  */
case object UndoEvent extends ViewGameEvent

/**
  * The player goes forward in turn history.
  */
case object RedoEvent extends ViewGameEvent

/**
  * The player goes to the starting state of his turn.
  */
case object ResetEvent extends ViewGameEvent

/**
  * The player sorts hand cards by suit.
  */
case object SortHandBySuitEvent extends ViewGameEvent

/**
  * The player sorts hand cards by rank.
  */
case object SortHandByRankEvent extends ViewGameEvent

/**
  * The player pass his turn.
  */
case object EndTurnEvent extends ViewGameEvent

/**
  * The player plays a combination of card from his hand.
  *
  * @param cards the sequence of card played by the player
  */
case class MakeCombinationEvent(cards: Seq[Card]) extends ViewGameEvent

/**
  * The player picks a combination of card from the game board.
  *
  * @param combinationId the identifier of the card combination
  */
case class PickCardCombinationEvent(combinationId: String) extends ViewGameEvent

/**
  * The player picks some cards from the game board.
  *
  * @param cards the sequence of card picked by the player
  */
case class PickCardsEvent(cards: Seq[Card]) extends ViewGameEvent

/**
  * The player updates an existing combination with some cards from his hand.
  *
  * @param combinationId the identifier of the card combination
  * @param cards         the sequence of card to be added to the card combination
  */
case class UpdateCardCombinationEvent(combinationId: String, cards: Seq[Card]) extends ViewGameEvent

/**
  * The player decided to play another time.
  */
case object PlayAgainEvent extends ViewGameEvent

case object TurnStartedEvent extends ViewGameEvent

