package it.parttimeteam.core

import it.parttimeteam.core.cards.Card
import it.parttimeteam.core.collections._
import it.parttimeteam.core.player.Player
import it.parttimeteam.core.player.Player._
import it.parttimeteam.{Board, GameState}

trait GameManager {

  /**
   * Create a new game state from players ids.
   *
   * @param players List of players ids.
   * @return New Game State.
   */
  def create(players: Seq[PlayerId]): GameState

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
   * @param combination Combination to validate.
   * @return True if is valid, false anywhere.
   */
  def validateCombination(combination: CardCombination): Boolean

  /**
   * Pick cards from a combination on the table.
   *
   * @param hand  Hand where put cards picked.
   * @param board Board where pick cards.
   * @param cards Cards to pick.
   * @return Hand and Board updated.
   */
  def pickBoardCards(hand: Hand, board: Board, cards: Card*): Either[String, (Hand, Board)]

  /**
   * Play cards from hand to board.
   *
   * @param hand        Hand where to pick cards.
   * @param board       Board where put cards.
   * @param combination Combination to pick.
   * @return Hand and Board updated. If hand doesn't contain any combination card, return exactly the same hand and board.
   */
  def playCombination(hand: Hand, board: Board, combination: CardCombination): (Hand, Board)
}

class GameManagerImpl extends GameManager {
  /**
   * @inheritdoc
   */
  override def create(ids: Seq[PlayerId]): GameState = {

    var deck: Deck = Deck.shuffled
    val playerList = ids.map(i => {
      val playerCards = deck.draw(13)
      deck = playerCards._1
      Player("", i, Hand(playerCards._2.toList))
    })

    GameState(deck,
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
    board.combinations.forall(c => validateCombination(c)) && hand.tableCards.isEmpty

  // TODO: This method is useful?
  /**
   * @inheritdoc
   */
  override def validateCombination(combination: CardCombination): Boolean = combination.isValid

  /**
   * @inheritdoc
   */
  override def pickBoardCards(hand: Hand,
                              board: Board,
                              cards: Card*): Either[String, (Hand, Board)] = {
    board.pickCards(cards).map(updatedBoard => (hand.addTableCards(cards), updatedBoard))
  }

  /**
   * @inheritdoc
   */
  override def playCombination(hand: Hand,
                               board: Board,
                               combination: CardCombination): (Hand, Board) = {

    val removed = hand.removeCards(combination.cards)
    removed match {
      case Right(value) => (value._1, board.addCombination(combination))
      case _ => (hand, board)
    }
  }
}