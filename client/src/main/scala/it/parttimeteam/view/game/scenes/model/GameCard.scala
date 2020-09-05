package it.parttimeteam.view.game.scenes.model

import it.parttimeteam.core.cards.Card
import it.parttimeteam.view.ViewConfig
import it.parttimeteam.view.utils.CardUtils
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.StackPane

/**
  * Represents the single card wrapped inside a StackPane.
  * Allows the player to select the card.
  */
trait GameCard extends StackPane with SelectableItem {
  /**
    * Mark the GameCard as a BoardCard. The player will be forced to play this card before passing his turn.
    */
  def setAsBoardCard(): Unit

  /**
    * Get the Card wrapped by GameCard
    *
    * @return the GameCard entity
    */
  def getCard: Card
}

object GameCard {

  def apply(card: Card): GameCard = new GameCardImpl(card: Card)

  class GameCardImpl(private val card: Card) extends GameCard {

    override def toString: String = card.toString

    val cardImage: ImageView = new ImageView(new Image(CardUtils.getCardPath(card)))
    cardImage.fitHeight = ViewConfig.HAND_CARD_HEIGHT
    cardImage.preserveRatio = true

    this.setAlignment(Pos.TopRight)
    this.getStyleClass.add("baseCard")
    this.getChildren.add(cardImage)

    /**
      * @inheritdoc
      */
    override def setSelected(selected: Boolean): Unit = {
      if (selected) {
        this.getStyleClass.add("cardSelected")
        this.setTranslateY(this.getTranslateY - ViewConfig.CARD_Y_TRANSLATION)
      } else {
        this.getStyleClass.remove("cardSelected")
        this.setTranslateY(this.getTranslateY + ViewConfig.CARD_Y_TRANSLATION)
      }
    }

    /**
      * @inheritdoc
      */
    override def setAsBoardCard(): Unit = {
      val prohibitionIcon: ImageView = new ImageView(new Image("images/prohibitionSign.png", 15, 15, false, false))
      prohibitionIcon.margin = Insets(5d)

      this.getStyleClass.remove("baseCard")
      this.getStyleClass.add("boardCard")

      this.getChildren.add(prohibitionIcon)
    }

    /**
      * @inheritdoc
      */
    override def getCard: Card = card
  }

}

