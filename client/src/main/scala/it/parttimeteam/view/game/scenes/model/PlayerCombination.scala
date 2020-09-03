package it.parttimeteam.view.game.scenes.model

import it.parttimeteam.core.cards.Card
import it.parttimeteam.core.collections.CardCombination
import it.parttimeteam.view.game.scenes.GameScene.BoardListener
import scalafx.geometry.Insets
import scalafx.scene.control.Button
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{BorderPane, HBox}

trait PlayerCombination extends BorderPane with SelectableItem {
  def getCombination: CardCombination
}

object PlayerCombination {

  class PlayerCombinationImpl(combination: CardCombination, boardListener: BoardListener) extends PlayerCombination {

    override def toString: String = combination.toString

    this.setPadding(Insets(10d))

    val combinationCards = new HBox()
    combinationCards.spacing = 10d

    val pickBtn = new Button()
    val img = new ImageView(new Image("images/pick.png"))
    img.fitHeight = 15d
    img.preserveRatio = true

    pickBtn.setGraphic(img)
    pickBtn.onAction = _ => boardListener.onPickCombinationClick(this)

    pickBtn.margin = Insets(0, 0, 0, 5)

    this.left = combinationCards
    this.right = pickBtn
    this.getStyleClass.add("combination")

    this.setOnMouseEntered(_ => {
      this.getStyleClass.add("combinationHover")
    })

    this.setOnMouseExited(_ => {
      this.getStyleClass.remove("combinationHover")
    })

    this.setOnMouseClicked(_ => boardListener.onCombinationClicked(this))

    for (card: Card <- combination.cards) {
      val playerCard: PlayerCard = PlayerCard(card)

      combinationCards.children.add(playerCard)
      playerCard.setOnMouseClicked(_ => boardListener.onBoardCardClicked(playerCard))
    }

    override def setSelected(selected: Boolean): Unit = {
      if (selected) {
        this.getStyleClass.add("combinationSelected")
      } else {
        this.getStyleClass.remove("combinationSelected")
      }
    }

    override def getCombination: CardCombination = combination
  }

}
