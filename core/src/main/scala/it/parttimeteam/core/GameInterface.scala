package it.parttimeteam.core

import it.parttimeteam.core.cards.Card
import it.parttimeteam.core.collections.{Board, Deck, Hand}
import it.parttimeteam.core.player.Player
import it.parttimeteam.core.player.Player.{PlayerId, PlayerName}

trait GameInterface {

  /**
   * Create a new game state from players ids.
   *
   * @param players Sequence of players ids.
   * @return New Game State.
   */
  def create(players: Seq[(PlayerId, PlayerName)]): GameState

  /**
   * Draw a card from the top of the deck.
   *
   * @param deck Deck to draw.
   * @return Deck tail and card drawn.
   */
  def draw(deck: Deck): (Deck, Card)

  /**
   * Validate a card sequence.
   *
   * @param cards Card Sequence to validate.
   * @return True if is valid, false anywhere.
   */
  def validateCombination(cards: Seq[Card]): Boolean

  /**
   * Validate an entire board and hand.
   *
   * @param board Board to validate.
   * @param hand  Hand to validate.
   * @return True if is valid, false anywhere.
   */
  def validateMove(board: Board, hand: Hand): Boolean

  /**
   * Validate an entire turn. Checks made are:
   * 1) Newest Board and Hand must be valid;
   * 2) Newest Hand size must be less than the oldest one;
   * 3) Newest Hand boardCards must be empty.
   *
   * @param board      Board to validate with startBoard.
   * @param startBoard Starting Board.
   * @param hand       Hand to validate with startHand.
   * @param startHand  Starting Hand.
   * @return True if is valid, false anywhere.
   */
  def validateTurn(board: Board, startBoard: Board, hand: Hand, startHand: Hand): Boolean

  /**
   * Pick cards from combinations on the board.
   *
   * @param hand  Hand where put cards picked.
   * @param board Board where pick cards.
   * @param cards Cards to pick.
   * @return An Either with a possible error or the Hand and Board updated.
   */
  def pickBoardCards(hand: Hand, board: Board, cards: Seq[Card]): Either[String, (Hand, Board)]

  /**
   * Play cards from hand to board.
   *
   * @param hand  Hand where pick cards.
   * @param board Board where put cards.
   * @param cards Cards to play.
   * @return Hand and Board updated. If there was an error, return it.
   */
  def playCombination(hand: Hand, board: Board, cards: Seq[Card]): Either[String, (Hand, Board)]

  /**
   * Update a combination in the board by his id with some cards.
   *
   * @param hand  Hand where to pick cards.
   * @param board Board with the combination to update.
   * @param id    Id of the combination to update.
   * @param cards Cards to put in the combination.
   * @return Updated Board and Hand.
   */
  def putCardsInCombination(hand: Hand, board: Board, id: String, cards: Seq[Card]): (Hand, Board)
}

class GameInterfaceImpl extends GameInterface {
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

    GameState(deck, Board.empty, playerList)
  }

  /**
   * @inheritdoc
   */
  override def draw(deck: Deck): (Deck, Card) = deck.draw()

  /**
   * @inheritdoc
   */
  override def validateCombination(cards: Seq[Card]): Boolean = cards.isValid

  /**
   * @inheritdoc
   */
  override def validateMove(board: Board, hand: Hand): Boolean =
    board.combinations.forall(c => validateCombination(c.cards)) && hand.boardCards.isEmpty

  /**
   * @inheritdoc
   */
  override def validateTurn(board: Board, startBoard: Board, hand: Hand, startHand: Hand): Boolean =
    validateMove(board, hand) &&
      (hand.playerCards.size < startHand.playerCards.size) &&
      hand.boardCards.isEmpty

  /**
   * @inheritdoc
   */
  override def pickBoardCards(hand: Hand,
                              board: Board,
                              cards: Seq[Card]): Either[String, (Hand, Board)] = {
    board.pickCards(cards).map(updatedBoard => (hand.addBoardCards(cards), updatedBoard))
  }

  /**
   * @inheritdoc
   */
  override def playCombination(hand: Hand,
                               board: Board,
                               cards: Seq[Card]): Either[String, (Hand, Board)] = {
    val orderedCards = cards sortByRank()
    if (this.validateCombination(orderedCards)) {
      hand.removeCards(cards).map(updateHand => (updateHand, board.putCombination(orderedCards)))
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