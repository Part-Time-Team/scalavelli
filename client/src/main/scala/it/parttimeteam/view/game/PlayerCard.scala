package it.parttimeteam.view.game

import it.parttimeteam.core.cards.Card
import it.parttimeteam.view.ViewConfig
import it.parttimeteam.view.utils.CardUtils
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.StackPane

trait PlayerCard extends StackPane {
  def setSelected(selected: Boolean): Unit

  def setAsBoardCard(): Unit

  def getCard: Card
}

object PlayerCard {

  def apply(card: Card): PlayerCard = new PlayerCardImpl(card: Card)

  class PlayerCardImpl(private val card: Card) extends PlayerCard {

    override def toString: String = card.toString

    val cardImage: ImageView = new ImageView(new Image(CardUtils.getCardPath(card)))
    cardImage.fitHeight = ViewConfig.HAND_CARD_HEIGHT
    cardImage.preserveRatio = true

    this.setAlignment(Pos.TopRight)
    this.getStyleClass.add("baseCard")
    this.getChildren.add(cardImage)

    override def setSelected(selected: Boolean): Unit = {
      if (selected) {
        this.getStyleClass.add("cardSelected")
        this.setTranslateY(this.getTranslateY - ViewConfig.CARD_Y_TRANSLATION)
      } else {
        this.getStyleClass.remove("cardSelected")
        this.setTranslateY(this.getTranslateY + ViewConfig.CARD_Y_TRANSLATION)
      }
    }

    override def setAsBoardCard(): Unit = {
      val prohibitionIcon: ImageView = new ImageView(new Image("images/prohibitionSign.png", 15, 15, false, false))
      prohibitionIcon.margin = Insets(5d)

      this.getStyleClass.remove("baseCard")
      this.getStyleClass.add("boardCard")

      this.getChildren.add(prohibitionIcon)
    }

    override def getCard: Card = card
  }

}

