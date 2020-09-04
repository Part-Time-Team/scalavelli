package it.parttimeteam.view.game

import it.parttimeteam.core.cards.Card

/**
  * Possible game view events which can be triggered by player.
  */
sealed class GameViewEvent

/**
  * The player leaves the game.
  */
case object LeaveGameViewEvent extends GameViewEvent

/**
  * The player goes backward in turn history.
  */
case object UndoViewEvent extends GameViewEvent

/**
  * The player goes forward in turn history.
  */
case object RedoViewEvent extends GameViewEvent

/**
  * The player goes to the starting state of his turn.
  */
case object ResetHistoryViewEvent extends GameViewEvent

/**
  * The player sorts hand cards by suit.
  */
case object SortHandBySuitViewEvent extends GameViewEvent

/**
  * The player sorts hand cards by rank.
  */
case object SortHandByRankViewEvent extends GameViewEvent

/**
  * The player pass his turn without drawing a card
  */
case object EndTurnViewEvent extends GameViewEvent

/**
  * The player pass his turn and draws a card
  */
case object EndTurnAndDrawViewEvent extends GameViewEvent

/**
  * The player plays a combination of card from his hand.
  *
  * @param cards the sequence of card played by the player
  */
case class MakeCombinationViewEvent(cards: Seq[Card]) extends GameViewEvent

/**
  * The player picks a combination of card from the game board.
  *
  * @param combinationId the identifier of the card combination
  */
case class PickCardCombinationViewEvent(combinationId: String) extends GameViewEvent

/**
  * The player picks some cards from the game board.
  *
  * @param cards the sequence of card picked by the player
  */
case class PickCardsViewEvent(cards: Seq[Card]) extends GameViewEvent

/**
  * The player updates an existing combination with some cards from his hand.
  *
  * @param combinationId the identifier of the card combination
  * @param cards         the sequence of card to be added to the card combination
  */
case class UpdateCardCombinationViewEvent(combinationId: String, cards: Seq[Card]) extends GameViewEvent

