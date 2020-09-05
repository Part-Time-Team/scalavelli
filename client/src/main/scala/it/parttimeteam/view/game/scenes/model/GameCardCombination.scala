package it.parttimeteam.view.game.scenes.model

import it.parttimeteam.core.cards.Card
import it.parttimeteam.core.collections.CardCombination
import it.parttimeteam.view.game.scenes.GameScene.BoardListener
import it.parttimeteam.view.game.scenes.panes.ActionGamePane
import scalafx.geometry.Insets
import scalafx.scene.control.Button
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{BorderPane, HBox}

/**
  * Represents the CardCombination wrapped inside a BorderPane.
  * Allows the player to select the CardCombination or to pick it from the game board.
  */
trait GameCardCombination extends BorderPane with ActionGamePane with SelectableItem {

  /**
    * Return the CardCombination wrapper by GameCardCombination
    *
    * @return the CardCombination entity
    */
  def getCombination: CardCombination
}

object GameCardCombination {

  class GameCardCombinationImpl(combination: CardCombination, boardListener: BoardListener) extends GameCardCombination {

    override def toString: String = combination.toString

    this.padding = Insets(10d)

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
      val playerCard: GameCard = GameCard(card, boardListener)

      combinationCards.children.add(playerCard)

      playerCard.setOnMouseClicked(e => {
        e.consume()
        boardListener.onCardClicked(playerCard)
      })
    }

    /**
      * @inheritdoc
      */
    override def setSelected(selected: Boolean): Unit = {
      if (selected) {
        this.getStyleClass.add("combinationSelected")
      } else {
        this.getStyleClass.remove("combinationSelected")
      }
    }

    /**
      * @inheritdoc
      */
    override def getCombination: CardCombination = combination

    /**
      * @inheritdoc
      */
    override def disableActions(): Unit = {
      combinationCards.children.forEach(playerCard => playerCard.setOnMouseClicked(e => {
        e.consume()
      }))
    }

    /**
      * @inheritdoc
      */
    override def enableActions(): Unit = {
      combinationCards.children.forEach(playerCard => playerCard.setOnMouseClicked(e => {
        e.consume()
        boardListener.onCardClicked(playerCard.asInstanceOf[GameCard])
      }))
    }
  }

}
