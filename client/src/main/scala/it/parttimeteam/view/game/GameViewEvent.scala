package it.parttimeteam.view.game

import it.parttimeteam.core.cards.Card

sealed class GameViewEvent

// Game ViewEvent
case class LeaveGameViewEvent() extends GameViewEvent

case class UndoViewEvent() extends GameViewEvent

case class RedoViewEvent() extends GameViewEvent

case class ResetHistoryViewEvent() extends GameViewEvent

case class SortHandBySuitViewEvent() extends GameViewEvent

case class SortHandByRankViewEvent() extends GameViewEvent

case class EndTurnViewEvent() extends GameViewEvent

case class EndTurnAndDrawViewEvent() extends GameViewEvent

case class MakeCombinationViewEvent(cards: Card*) extends GameViewEvent

case class PickCardCombinationViewEvent(combinationId: String) extends GameViewEvent

// TODO: discuss with other developers
case class PickCardsViewEvent(cards: Card*) extends GameViewEvent

case class UpdateCardCombinationViewEvent(combinationId: String, card: Card, indexToAdd: Int) extends GameViewEvent

