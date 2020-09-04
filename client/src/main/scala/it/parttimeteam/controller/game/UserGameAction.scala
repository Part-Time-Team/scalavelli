package it.parttimeteam.controller.game

import it.parttimeteam.core.cards.Card

/**
  * Possible game view events which can be triggered by player.
  */
sealed class UserGameAction

/**
  * The player leaves the game.
  */
case object LeaveGameAction extends UserGameAction

/**
  * The player goes backward in turn history.
  */
case object UndoAction extends UserGameAction

/**
  * The player goes forward in turn history.
  */
case object RedoAction extends UserGameAction

/**
  * The player goes to the starting state of his turn.
  */
case object ResetAction extends UserGameAction

/**
  * The player sorts hand cards by suit.
  */
case object SortHandBySuitAction extends UserGameAction

/**
  * The player sorts hand cards by rank.
  */
case object SortHandByRankAction extends UserGameAction

/**
  * The player pass his turn without drawing a card
  */
case object EndTurnAction extends UserGameAction

/**
  * The player pass his turn and draws a card
  */
case object EndTurnAndDrawAction extends UserGameAction

/**
  * The player plays a combination of card from his hand.
  *
  * @param cards the sequence of card played by the player
  */
case class MakeCombinationAction(cards: Seq[Card]) extends UserGameAction

/**
  * The player picks a combination of card from the game board.
  *
  * @param combinationId the identifier of the card combination
  */
case class PickCardCombinationAction(combinationId: String) extends UserGameAction

/**
  * The player picks some cards from the game board.
  *
  * @param cards the sequence of card picked by the player
  */
case class PickCardsAction(cards: Seq[Card]) extends UserGameAction

/**
  * The player updates an existing combination with some cards from his hand.
  *
  * @param combinationId the identifier of the card combination
  * @param cards         the sequence of card to be added to the card combination
  */
case class UpdateCardCombinationAction(combinationId: String, cards: Seq[Card]) extends UserGameAction

