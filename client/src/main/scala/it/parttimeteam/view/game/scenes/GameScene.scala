package it.parttimeteam.view.game.scenes

import it.parttimeteam.core.cards.Card
import it.parttimeteam.gamestate.{Opponent, PlayerGameState}
import it.parttimeteam.view.game.listeners.GameSceneListener
import it.parttimeteam.view.utils.{CardUtils, MachiavelliButton, MachiavelliLabel}
import javafx.scene.layout.StackPane
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control._
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{BorderPane, HBox, TilePane, VBox}
import scalafx.stage.{Modality, Stage, StageStyle}

/**
  * Allow to participate to create a private game by inserting the number of players.
  * Obtains a private code to share to invite other players.
  **/
trait GameScene extends Scene {
  def matchReady(): Unit

  def showInitMatch(): Unit

  def setState(state: PlayerGameState): Unit
}

class GameSceneImpl(val listener: GameSceneListener) extends GameScene {
  stylesheets.add("/styles/gameStyle.css")

  var initMatchDialog: Stage = _
  var selectedCards: Seq[Card] = Seq()

  // init right bar
  val right = new BorderPane()
  right.padding = Insets(10d)
  val rightTop = new VBox()
  val rightBottom = new VBox()

  val btnContainer = new VBox()
  btnContainer.spacing = 5d
  val historyBtnContainer = new HBox()
  historyBtnContainer.prefWidth <== btnContainer.width
  historyBtnContainer.alignment = Pos.Center
  historyBtnContainer.spacing = 5d

  val undoBtn = MachiavelliButton("Undo", undoClick, "images/undo.png")
  val redoBtn = MachiavelliButton("Redo", redoClick, "images/redo.png")
  val nextBtn = MachiavelliButton("Next", nextTurnClick)

  nextBtn.prefWidth <== rightBottom.width

  historyBtnContainer.children.addAll(undoBtn, redoBtn)
  btnContainer.children.addAll(nextBtn, historyBtnContainer)

  val stateContainer = new VBox()
  val stateTitle = MachiavelliLabel("Time")
  val stateMessage = MachiavelliLabel("03:00")

  stateContainer.children.addAll(stateTitle, stateMessage)

  val otherPlayersContainer = new TilePane()
  otherPlayersContainer.setPrefColumns(2)

  rightTop.children.addAll(stateContainer, otherPlayersContainer)
  rightBottom.children.add(btnContainer)

  right.top = rightTop
  right.bottom = rightBottom

  val tableCombinations = new VBox()
  tableCombinations.spacing = 10d

  // init board
  val center = new ScrollPane()
  center.setContent(tableCombinations)

  // init bottom
  val bottom = new VBox()
  val actionBar = new HBox()

  val combinationBtn = MachiavelliButton("Make Combination", makeCombinationClick)
  val clearCombinationBtn = MachiavelliButton("Clear Selection", clearSelectionClick)

  val handPane = new ScrollPane()

  actionBar.children.addAll(combinationBtn, clearCombinationBtn)

  val handCardsContainer = new HBox()
  handCardsContainer.spacing = 5d
  handCardsContainer.padding = Insets(5d)

  handPane.setContent(handCardsContainer)

  bottom.children.addAll(actionBar, handPane)

  val borderPane: BorderPane = new BorderPane()

  borderPane.bottom = bottom
  borderPane.right = right
  borderPane.center = center

  root = borderPane

  def removeCard(imageViewWrapper: StackPane, card: Card): Unit = {
    selectedCards = selectedCards.filter(c => c != card)
    System.out.println(s"Selected Cards ${selectedCards.toString()}")
    imageViewWrapper.getStyleClass.remove("cardSelected")
  }

  def addCard(imageViewWrapper: StackPane, card: Card): Unit = {
    selectedCards = selectedCards :+ card
    System.out.println(s"Selected Cards ${selectedCards.toString()}")
    imageViewWrapper.getStyleClass.add("cardSelected")
  }

  override def setState(state: PlayerGameState): Unit = {
    for (card <- state.hand.playerCards) {
      val cardImage: ImageView = new ImageView(new Image(CardUtils.getCardPath(card)))
      cardImage.fitWidth = 80d
      cardImage.preserveRatio = true

      val imageViewWrapper = new StackPane()
      imageViewWrapper.setAlignment(Pos.TopRight)
      imageViewWrapper.getChildren.add(cardImage)
      handCardsContainer.children.add(imageViewWrapper)

      imageViewWrapper.setOnMouseClicked(_ => {
        if (selectedCards.contains(card)) {
          removeCard(imageViewWrapper, card)
        } else {
          addCard(imageViewWrapper, card)
        }

      })
    }

    for (card <- state.hand.tableCards) {
      val cardImage: ImageView = new ImageView(new Image(CardUtils.getCardPath(card)))
      cardImage.fitWidth = 80d
      cardImage.preserveRatio = true

      val prohibitionIcon: ImageView = new ImageView(new Image("images/prohibitionSign.png", 15, 15, false, false))
      prohibitionIcon.margin = Insets(5d)
      val imageViewWrapper = new StackPane()
      imageViewWrapper.setAlignment(Pos.TopRight)
      imageViewWrapper.getChildren.addAll(cardImage, prohibitionIcon)
      handCardsContainer.children.add(imageViewWrapper)

      imageViewWrapper.setOnMouseClicked(_ => {
        if (selectedCards.contains(card)) {
          removeCard(imageViewWrapper, card)
        } else {
          addCard(imageViewWrapper, card)
        }

      })
    }

    state.board.combinationsList.zipWithIndex foreach {
      case (combination, index) => {
        val combinationContainer = new BorderPane()

        combinationContainer.setPadding(Insets(10d))

        val combinationCards = new HBox()
        combinationCards.spacing = 10d

        val pickBtn = new Button()
        val img = new ImageView(new Image("images/pick.png"))
        img.fitHeight = 15d
        img.preserveRatio = true

        pickBtn.setGraphic(img)
        pickBtn.onAction = _ => pickCombinationClick(index)

        pickBtn.margin = Insets(0, 0, 0, 5)

        combinationContainer.left = combinationCards
        combinationContainer.right = pickBtn
        combinationContainer.getStyleClass.add("combination")

        for (card: Card <- combination.cards) {
          val cardImage: ImageView = new ImageView(new Image(CardUtils.getCardPath(card)))
          cardImage.fitWidth = 80d
          cardImage.preserveRatio = true
          combinationCards.children.add(cardImage)
        }

        tableCombinations.children.add(combinationContainer)
      }
    }

    for (player: Opponent <- state.otherPlayers) {
      val nameLabel = MachiavelliLabel(player.name)
      val cardsNumberLabel = MachiavelliLabel(player.cardsNumber.toString)

      val playerInfoContainer: VBox = new VBox()
      val cardInfoContainer: HBox = new HBox()

      val cardImage: ImageView = new ImageView(new Image("images/cards/backBlue.png"))
      cardImage.fitWidth = 20d
      cardImage.preserveRatio = true

      cardInfoContainer.children.addAll(cardImage, cardsNumberLabel)
      playerInfoContainer.children.addAll(nameLabel, cardInfoContainer)

      otherPlayersContainer.children.add(playerInfoContainer)
    }
  }

  override def showInitMatch(): Unit = {
    initMatchDialog = new Stage()
    val progressBar: ProgressBar = new ProgressBar()
    initMatchDialog.initStyle(StageStyle.Decorated)
    initMatchDialog.setResizable(false)
    initMatchDialog.initModality(Modality.ApplicationModal)
    initMatchDialog.setTitle("Game loading")
    val label = new Label("Please wait...")

    val vb = new VBox()
    vb.setSpacing(5)
    vb.setAlignment(Pos.Center)
    vb.getChildren.addAll(label, progressBar)
    val scene = new Scene(vb)
    initMatchDialog.setScene(scene)

    initMatchDialog.show()
    initMatchDialog.setAlwaysOnTop(true)
  }

  override def matchReady(): Unit = {
    initMatchDialog.hide()
  }

  def makeCombinationClick(): Unit = {
    System.out.println("makeCombinationClick")
  }

  def clearSelectionClick(): Unit = {
    handCardsContainer.children.forEach(p => {
      val cardWrapper: StackPane = p.asInstanceOf[StackPane]
      cardWrapper.getStyleClass.remove("cardSelected")
    })

    selectedCards = selectedCards.empty
    System.out.println(s"Selected Cards ${selectedCards.toString()}")
  }

  def redoClick(): Unit = {
    System.out.println("redoClick")
  }

  def undoClick(): Unit = {
    System.out.println("undoClick")
  }

  def pickCombinationClick(combinationIndex: Int): Unit = {
    System.out.println(s"pickCombinationClick $combinationIndex")
  }

  def nextTurnClick(): Unit = {
    System.out.println(s"nextTurnClick")
  }

}

