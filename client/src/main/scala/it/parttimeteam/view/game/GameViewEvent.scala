package it.parttimeteam.view.game

import it.parttimeteam.core.cards.Card

sealed class GameViewEvent

// Game ViewEvent
case object LeaveGameViewEvent extends GameViewEvent

case object UndoViewEvent extends GameViewEvent

case object RedoViewEvent extends GameViewEvent

case object ResetHistoryViewEvent extends GameViewEvent

case object SortHandBySuitViewEvent extends GameViewEvent

case object SortHandByRankViewEvent extends GameViewEvent

case object EndTurnViewEvent extends GameViewEvent

case object EndTurnAndDrawViewEvent extends GameViewEvent

case class MakeCombinationViewEvent(cards: Seq[Card]) extends GameViewEvent

case class PickCardCombinationViewEvent(combinationId: String) extends GameViewEvent

// TODO: discuss with other developers
case class PickCardsViewEvent(cards: Card*) extends GameViewEvent

case class UpdateCardCombinationViewEvent(combinationId: String, card: Card, indexToAdd: Int) extends GameViewEvent

