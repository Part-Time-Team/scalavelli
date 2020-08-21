package it.parttimeteam.view.game

import it.parttimeteam.core.cards.Card

sealed class GameViewEvent

// Game ViewEvent
case class MakeCombinationViewEvent(cards: Seq[Card]) extends GameViewEvent

case class PreviousStateViewEvent() extends GameViewEvent

case class NextStateViewEvent() extends GameViewEvent

case class PickCardCombinationViewEvent(cardCombinationIndex: Int) extends GameViewEvent

case class EndTurnViewEvent() extends GameViewEvent


