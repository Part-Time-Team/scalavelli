package it.parttimeteam.view

import it.parttimeteam.core.cards.Card

sealed class ViewEvent

// StartUp ViewEvent
case class PublicGameSubmitViewEvent(username: String, playersNumber: Int) extends ViewEvent

case class PrivateGameSubmitViewEvent(username: String, privateCode: String) extends ViewEvent

case class CreatePrivateGameSubmitViewEvent(username: String, playersNumber: Int) extends ViewEvent

case class LeaveLobbyViewEvent(userId: String) extends ViewEvent


// Game ViewEvent
case class MakeCombinationViewEvent(cards: Seq[Card]) extends ViewEvent

case class PreviousStateViewEvent() extends ViewEvent

case class NextStateViewEvent() extends ViewEvent

case class PickCardCombinationViewEvent(cardCombinationIndex: Int) extends ViewEvent

case class EndTurnViewEvent() extends ViewEvent


