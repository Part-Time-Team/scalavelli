package it.parttimeteam.view.game.scenes

import it.parttimeteam.core.cards.Card
import it.parttimeteam.core.collections.Hand
import it.parttimeteam.view.game.listeners.GameSceneListener
import it.parttimeteam.view.utils.{CardUtils, MachiavelliButton}
import javafx.scene.layout.StackPane
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.ScrollPane
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{HBox, VBox}

class BottomBar(listener: GameSceneListener) extends VBox {
  var selectedCards: Seq[Card] = Seq()

  val actionBar = new HBox()

  val combinationBtn = MachiavelliButton("Make Combination", () => makeCombinationClick())
  val clearCombinationBtn = MachiavelliButton("Clear Selection", () => clearSelectionClick())
  actionBar.children.addAll(combinationBtn, clearCombinationBtn)

  val handPane = new ScrollPane()

  val handCardsContainer = new HBox()
  handCardsContainer.spacing = 5d
  handCardsContainer.padding = Insets(5d)

  handPane.setContent(handCardsContainer)

  children.addAll(actionBar, handPane)

  def setHand(hand: Hand): Unit = {
    for (card <- hand.playerCards) {
      addHandCard(card, isTableCard = false)
    }

    for (card <- hand.tableCards) {
      addHandCard(card, isTableCard = true)
    }
  }

  private def addHandCard(card: Card, isTableCard: Boolean): Unit = {
    val cardImage: ImageView = new ImageView(new Image(CardUtils.getCardPath(card)))
    cardImage.fitWidth = 80d
    cardImage.preserveRatio = true

    val imageViewWrapper = new StackPane()
    imageViewWrapper.setAlignment(Pos.TopRight)

    if (isTableCard) {
      val prohibitionIcon: ImageView = new ImageView(new Image("images/prohibitionSign.png", 15, 15, false, false))
      prohibitionIcon.margin = Insets(5d)
      imageViewWrapper.getChildren.addAll(cardImage, prohibitionIcon)
    } else {
      imageViewWrapper.getChildren.add(cardImage)
    }

    handCardsContainer.children.add(imageViewWrapper)

    imageViewWrapper.setOnMouseClicked(_ => {
      if (selectedCards.contains(card)) {
        removeCardToSelection(imageViewWrapper, card)
      } else {
        addCardToSelection(imageViewWrapper, card)
      }
    })
  }

  private def removeCardToSelection(imageViewWrapper: StackPane, card: Card): Unit = {
    selectedCards = selectedCards.filter(c => c != card)
    System.out.println(s"Selected Cards ${selectedCards.toString()}")
    imageViewWrapper.getStyleClass.remove("cardSelected")
  }

  private def addCardToSelection(imageViewWrapper: StackPane, card: Card): Unit = {
    selectedCards = selectedCards :+ card
    System.out.println(s"Selected Cards ${selectedCards.toString()}")
    imageViewWrapper.getStyleClass.add("cardSelected")
  }

  private def makeCombinationClick(): Unit = {
    System.out.println("makeCombinationClick")
    listener.makeCombination(selectedCards)
  }

  private def clearSelectionClick(): Unit = {
    handCardsContainer.children.forEach(p => {
      val cardWrapper: StackPane = p.asInstanceOf[StackPane]
      cardWrapper.getStyleClass.remove("cardSelected")
    })

    selectedCards = List()
    System.out.println(s"Selected Cards ${selectedCards.toString()}")
  }
}
