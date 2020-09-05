package it.parttimeteam.core

import it.parttimeteam.core
import it.parttimeteam.core.cards.Card
import it.parttimeteam.core.collections._
import it.parttimeteam.core.player.Player
import it.parttimeteam.core.player.Player._

trait GameManager {

  /**
   * Create a new game state from players ids.
   *
   * @param players List of players ids.
   * @return New Game State.
   */
  def create(players: Seq[(PlayerId, PlayerName)]): GameState

  /**
   * Draw a card from the top of the deck.
   *
   * @param deck Deck to draw.
   * @return Deck tail and card picked.
   */
  def draw(deck: Deck): (Deck, Card)

  /**
   * Validate an entire board and hand.
   *
   * @param board Board to validate.
   * @param hand  Hand to validate.
   * @return True if is valid, false anywhere.
   */
  def validateTurn(board: Board, hand: Hand): Boolean

  /**
   * Validate a card combination.
   *
   * @param cards Combination to validate.
   * @return True if is valid, false anywhere.
   */
  def validateCombination(cards: Seq[Card]): Boolean

  /**
   * Pick cards from a combination on the table.
   *
   * @param hand  Hand where put cards picked.
   * @param board Board where pick cards.
   * @param cards Cards to pick.
   * @return Hand and Board updated.
   */
  def pickBoardCards(hand: Hand, board: Board, cards: Seq[Card]): Either[String, (Hand, Board)]

  /**
   * Play cards from hand to board.
   *
   * @param hand  Hand where to pick cards.
   * @param board Board where put cards.
   * @param cards cards to pick.
   * @return Hand and Board updated. If hand doesn't contain any combination card, return exactly the same hand and board.
   * @todo Take only a card seq to add to board.
   */
  def playCombination(hand: Hand, board: Board, cards: Seq[Card]): Either[String, (Hand, Board)]

  /**
   * Update a combination in the board by his id with some cards.
   *
   * @param hand  Hand where to pick cards.
   * @param board Board with the combination to update.
   * @param id    Id of the combnation to update.
   * @param cards Cards to pur in the combination.
   * @return Updated board.
   */
  def putCardsInCombination(hand: Hand, board: Board, id: String, cards: Seq[Card]): (Hand, Board)
}

class GameManagerImpl extends GameManager {
  /**
   * @inheritdoc
   */
  override def create(ids: Seq[(PlayerId, PlayerName)]): GameState = {

    var deck: Deck = Deck.shuffled
    val playerList = ids.map(i => {
      val playerCards = deck.draw(13)
      deck = playerCards._1
      Player(i._2, i._1, Hand(playerCards._2.toList))
    })

    core.GameState(deck,
      Board.empty,
      playerList)
  }

  /**
   * @inheritdoc
   */
  override def draw(deck: Deck): (Deck, Card) = deck.draw()

  /**
   * @inheritdoc
   */
  override def validateTurn(board: Board, hand: Hand): Boolean =
    board.combinations.forall(c => validateCombination(c.cards)) && hand.tableCards.isEmpty

  // TODO: This method is useful?
  /**
   * @inheritdoc
   */
  override def validateCombination(cards: Seq[Card]): Boolean = cards.isValid

  /**
   * @inheritdoc
   */
  override def pickBoardCards(hand: Hand,
                              board: Board,
                              cards: Seq[Card]): Either[String, (Hand, Board)] = {
    board.pickCards(cards).map(updatedBoard => (hand.addTableCards(cards), updatedBoard))
  }

  /**
   * @inheritdoc
   */
  override def playCombination(hand: Hand,
                               board: Board,
                               cards: Seq[Card]): Either[String, (Hand, Board)] = {
    
    val orderedCards = cards sortByRank()
    if (this.validateCombination(orderedCards)) {
      hand.removeCards(orderedCards).map(updateHand => (updateHand, board.putCombination(orderedCards)))
    } else {
      Left("Combination not valid")
    }
  }

  /**
   * @inheritdoc
   */
  override def putCardsInCombination(hand: Hand, board: Board, id: String, cards: Seq[Card]): (Hand, Board) = {
    val put = hand.removeCards(cards)
    put match {
      case Right(value) => (value, board.putCards(id, cards))
      case _ => (hand, board)
    }
  }
}