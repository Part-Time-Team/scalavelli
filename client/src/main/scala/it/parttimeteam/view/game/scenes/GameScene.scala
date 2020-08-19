package it.parttimeteam.view.game.scenes

import it.parttimeteam.core.collections.CardCombination
import it.parttimeteam.gamestate.PlayerGameState
import it.parttimeteam.view.ViewConfig
import it.parttimeteam.view.game.listeners.GameSceneListener
import it.parttimeteam.view.utils.{CardUtils, MachiavelliButton, MachiavelliLabel}
import javafx.scene.layout.StackPane
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control._
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{BorderPane, HBox, TilePane, VBox}

/**
  * Allow to participate to create a private game by inserting the number of players.
  * Obtains a private code to share to invite other players.
  **/
trait GameScene extends Scene {
  def setState(state: PlayerGameState): Unit
}

class GameSceneImpl(val listener: GameSceneListener) extends GameScene {
  stylesheets.add("/styles/gameStyle.css")

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

  handPane.setContent(handCards)

  bottom.children.addAll(actionBar, handPane)

  val borderPane: BorderPane = new BorderPane()

  borderPane.bottom = bottom
  borderPane.right = right
  borderPane.center = center

  root = borderPane

  override def setState(state: PlayerGameState): Unit = {
    for (card <- state.hand.playerCards){
      val cardImage: ImageView = new ImageView(new Image(CardUtils.getCardPath(card)))
      cardImage.fitWidth = 80d
      cardImage.preserveRatio = true
      handCards.children.add(cardImage)
    }

    for (card <- state.hand.tableCards){
      val cardImage: ImageView = new ImageView(new Image(CardUtils.getCardPath(card)))
      cardImage.fitWidth = 80d
      cardImage.preserveRatio = true

      val prohibitionIcon: ImageView = new ImageView(new Image("images/prohibitionSign.png", 15, 15, false, false))
      prohibitionIcon.margin = Insets(5d)
      val imageViewWrapper = new StackPane()
      imageViewWrapper.setAlignment(Pos.TopRight)
      imageViewWrapper.getChildren.addAll(cardImage, prohibitionIcon)
      handCards.children.add(imageViewWrapper)
    }

    for (combination: CardCombination <- state.board.combinationsList){
      val comb = new HBox()
      comb.setPadding(Insets(10d))
      comb.spacing = 10d
      comb.getStyleClass.add("combination")

      for (card <- combination.cards){
        val cardImage: ImageView = new ImageView(new Image(CardUtils.getCardPath(card)))
        cardImage.fitWidth = 80d
        cardImage.preserveRatio = true
        comb.children.add(cardImage)
      }

      val pickBtn = MachiavelliButton("Pick", null)
      comb.children.add(pickBtn)

      tableCombinations.children.add(comb)
    }
  }
}

