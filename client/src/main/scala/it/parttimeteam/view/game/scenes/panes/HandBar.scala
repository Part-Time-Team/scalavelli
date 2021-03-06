package it.parttimeteam.view.game.scenes.panes

import it.parttimeteam.core.cards.Card
import it.parttimeteam.core.collections.Hand
import it.parttimeteam.view.ViewConfig
import it.parttimeteam.view.game.scenes.GameScene.HandListener
import it.parttimeteam.view.game.scenes.model.GameCard
import scalafx.geometry.Insets
import scalafx.geometry.Pos.BottomCenter
import scalafx.scene.control.ScrollPane
import scalafx.scene.layout.HBox

/**
  * Pane which contains all the cards in player hand.
  */
trait HandBar extends ScrollPane with ActionGamePane {

  /**
    * Sets the hand inside a ScrollPane.
    *
    * @param hand the player Hand
    */
  def setHand(hand: Hand): Unit
}

object HandBar {

  class HandBarImpl(handListener: HandListener) extends HandBar {
    this.getStyleClass.add("transparent")

    val handCardsContainer = new HBox()
    handCardsContainer.spacing = ViewConfig.DEFAULT_SPACING
    handCardsContainer.padding = Insets(ViewConfig.CARD_Y_TRANSLATION + ViewConfig.HAND_CARD_PADDING, ViewConfig.HAND_CARD_PADDING, ViewConfig.HAND_CARD_PADDING, ViewConfig.HAND_CARD_PADDING)

    this.setMinHeight(ViewConfig.HAND_CARD_HEIGHT + ViewConfig.CARD_Y_TRANSLATION + ViewConfig.HAND_PADDING)

    this.setContent(handCardsContainer)

    /** @inheritdoc*/
    override def setHand(hand: Hand): Unit = {
      handCardsContainer.children.clear()

      for (card <- hand.playerCards) {
        addHandCard(card, isBoardCard = false)
      }

      for (card <- hand.boardCards) {
        addHandCard(card, isBoardCard = true)
      }
    }

    /** @inheritdoc*/
    override def disableActions(): Unit = {
      handCardsContainer.children.forEach(playerCard => {
        playerCard.asInstanceOf[GameCard].disableActions()
      })
    }

    /** @inheritdoc*/
    override def enableActions(): Unit = {
      handCardsContainer.children.forEach(playerCard => {
        playerCard.asInstanceOf[GameCard].enableActions()
      })
    }

    private def addHandCard(card: Card, isBoardCard: Boolean): Unit = {
      val playerCard: GameCard = GameCard(card, handListener)
      if (isBoardCard) {
        playerCard.setAsBoardCard()
      }

      handCardsContainer.children.add(playerCard.asInstanceOf[GameCard])
      handCardsContainer.alignment = BottomCenter
    }
  }
}


