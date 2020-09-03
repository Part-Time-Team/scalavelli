package it.parttimeteam.view.game.scenes.panes

import it.parttimeteam.core.cards.Card
import it.parttimeteam.core.collections.Hand
import it.parttimeteam.view.ViewConfig
import it.parttimeteam.view.game.PlayerCard
import it.parttimeteam.view.game.listeners.GameSceneToStageListener
import it.parttimeteam.view.game.scenes.GameScene.HandListener
import scalafx.geometry.Insets
import scalafx.geometry.Pos.BottomCenter
import scalafx.scene.control.ScrollPane
import scalafx.scene.layout.HBox

trait BottomBar extends ScrollPane {
  def setHand(hand: Hand): Unit
}

object BottomBar {

  class BottomBarImpl(listener: GameSceneToStageListener, handListener: HandListener) extends BottomBar {

    val handCardsContainer = new HBox()
    handCardsContainer.spacing = 5d
    handCardsContainer.padding = Insets(ViewConfig.CARD_Y_TRANSLATION + 5d, 5d, 5d, 5d)

    this.setContent(handCardsContainer)

    override def setHand(hand: Hand): Unit = {
      for (card <- hand.playerCards) {
        addHandCard(card, isBoardCard = false)
      }

      for (card <- hand.tableCards) {
        addHandCard(card, isBoardCard = true)
      }
    }

    private def addHandCard(card: Card, isBoardCard: Boolean): Unit = {
      val playerCard: PlayerCard = PlayerCard(card)
      if (isBoardCard) {
        playerCard.setAsBoardCard()
      }


      handCardsContainer.children.add(playerCard)
      handCardsContainer.alignment = BottomCenter

      playerCard.setOnMouseClicked(_ => handListener.onHandCardClicked(playerCard))
    }
  }

}


