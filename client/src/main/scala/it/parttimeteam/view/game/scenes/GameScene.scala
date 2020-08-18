package it.parttimeteam.view.game.scenes

import it.parttimeteam.Board
import it.parttimeteam.core.cards.Card
import it.parttimeteam.core.collections.{CardCombination, Hand}
import it.parttimeteam.gamestate.PlayerGameState
import it.parttimeteam.view.ViewConfig
import it.parttimeteam.view.game.listeners.GameSceneListener
import it.parttimeteam.view.utils.{CardUtils, MachiavelliButton, MachiavelliLabel}
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control._
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{BorderPane, HBox, TilePane, VBox}

class GameScene(val listener: GameSceneListener) extends Scene() {
  stylesheets.add("/styles/gameStyle.css")
  var board: Board = Board()
  board = board.addCombination(CardCombination(List(new Card("A", "♠"), new Card("2", "♠"))))
  board = board.addCombination(CardCombination(List(new Card("3", "♠"), new Card("4", "♠"))))
  board = board.addCombination(CardCombination(List(new Card("5", "♠"), new Card("6", "♠"), new Card("7", "♠"))))

  var hand: Hand = new Hand()
  hand = hand.addPlayerCards(new Card("A", "♥"),
    new Card("2", "♥"),
    new Card("3", "♥"),
    new Card("4", "♥"),
    new Card("5", "♥"),
    new Card("6", "♥"),
    new Card("7", "♥"))

  hand = hand.addTableCards(new Card("K", "♥"))

  val currState: PlayerGameState = new PlayerGameState(board, hand, null)

  val label: Label = MachiavelliLabel("Hello Game", ViewConfig.formLabelFontSize)

  val right = new VBox()
  val state = new VBox()
  val stateTitle = MachiavelliLabel("Time")
  val stateMessage = MachiavelliLabel("03:00")

  state.children.addAll(stateTitle, stateMessage)

  val otherPlayers = new TilePane()
  otherPlayers.setPrefColumns(2)

  right.children.addAll(state, otherPlayers)

  val tableCombinations = new VBox()
  tableCombinations.spacing = 10d

  val combinations: List[CardCombination] = currState.board.combinationsList

  for (combination: CardCombination <- combinations){
    val comb = new HBox()
    comb.setPadding(Insets(10d))
    comb.spacing = 10d
    comb.getStyleClass.add("combination")

    for (card <- combination.cards){
      val cardImage: ImageView = new ImageView(new Image(CardUtils.getCardPath(card)))
      cardImage.prefWidth(50)
      cardImage.preserveRatio = true
      comb.children.add(cardImage)
    }

    val pickBtn = MachiavelliButton("Pick", null)
    comb.children.add(pickBtn)

    tableCombinations.children.add(comb)
  }

  val center = new ScrollPane()
  center.setContent(tableCombinations)

  val bottom = new VBox()
  val actionBar = new HBox()

  val endTurnBtn = MachiavelliButton("Next", null)
  val handPane = new ScrollPane()

  actionBar.children.addAll(endTurnBtn)

  val handCards = new HBox()
  handCards.spacing = 5d
  handCards.padding = Insets(5d)

  for (card <- currState.hand.playerCards){
    val cardImage: ImageView = new ImageView(new Image(CardUtils.getCardPath(card)))
    cardImage.prefWidth(50)
    cardImage.preserveRatio = true
    handCards.children.add(cardImage)
  }

  for (card <- currState.hand.tableCards){
    val cardImage: ImageView = new ImageView(new Image(CardUtils.getCardPath(card)))
    cardImage.prefWidth(50)
    cardImage.preserveRatio = true

    val imageViewWrapper = new BorderPane()
    imageViewWrapper.center = cardImage
    imageViewWrapper.getStyleClass.add("tableCard")
    handCards.children.add(cardImage)
  }

  handPane.setContent(handCards)

  bottom.children.addAll(actionBar, handPane)

  val borderPane: BorderPane = new BorderPane()

  borderPane.bottom = bottom
  borderPane.right = right
  borderPane.center = center

  root = borderPane
}

