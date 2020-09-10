package it.parttimeteam.model.game

import it.parttimeteam.core.cards.Card

/**
  * Exposes all the game functionalities
  */
trait GameService {

  def updateCardCombination(combinationId: String, cards: Seq[Card]): Unit

  def pickCardsFromBoard(cards: Seq[Card]): Unit

  def pickCardCombination(combinationId: String): Unit

  def makeCombination(cards: Seq[Card]): Unit

  def endTurnWithMoves(): Unit

  def endTurnDrawingACard(): Unit

  def sortHandByRank(): Unit

  def sortHandBySuit(): Unit

  def resetTurnState(): Unit

  def redoTurn(): Unit

  def undoTurn(): Unit

  def leaveGame(): Unit

  def playerReady(): Unit

}
