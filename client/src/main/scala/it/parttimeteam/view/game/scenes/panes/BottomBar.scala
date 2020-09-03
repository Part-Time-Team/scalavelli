package it.parttimeteam.view.game.scenes.panes

import it.parttimeteam.core.cards.Card
import it.parttimeteam.core.collections.Hand
import it.parttimeteam.view.ViewConfig
import it.parttimeteam.view.game.PlayerCard
import it.parttimeteam.view.game.listeners.GameSceneToStageListener
import it.parttimeteam.view.game.scenes.HandListener
import it.parttimeteam.view.utils.MachiavelliButton
import scalafx.geometry.Insets
import scalafx.geometry.Pos.BottomCenter
import scalafx.scene.control.ScrollPane
import scalafx.scene.layout.{HBox, VBox}

class BottomBar(listener: GameSceneToStageListener, handListener: HandListener) extends VBox {
  val actionBar = new HBox()

  val combinationBtn = MachiavelliButton("Make Combination", () => makeCombinationClick())
  val clearHandSelectionBtn = MachiavelliButton("Clear Selection", () => clearSelectionClick())
  val pickCardsBtn = MachiavelliButton("Pick Cards", () => pickCardsClick())

  combinationBtn.setDisable(true)
  clearHandSelectionBtn.setDisable(true)
  pickCardsBtn.setDisable(true)

  actionBar.children.addAll(combinationBtn, clearHandSelectionBtn, pickCardsBtn)

  val handPane = new ScrollPane()

  val handCardsContainer = new HBox()
  handCardsContainer.spacing = 5d
  handCardsContainer.padding = Insets(ViewConfig.CARD_Y_TRANSLATION + 5d, 5d, 5d, 5d)

  handPane.setContent(handCardsContainer)

  children.addAll(actionBar, handPane)

  def setHand(hand: Hand): Unit = {
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

  private def makeCombinationClick(): Unit = {
    listener.makeCombination()
  }

  def setMakeCombinationEnabled(enable: Boolean): Unit = {
    combinationBtn.setDisable(!enable)
  }

  def setPickCardsEnabled(enable: Boolean): Unit = {
    pickCardsBtn.setDisable(!enable)
  }

  def setClearSelectionEnabled(enable: Boolean): Unit = {
    clearHandSelectionBtn.setDisable(!enable)
  }

  private def clearSelectionClick(): Unit = {
    listener.clearHandSelection()
  }

  def pickCardsClick(): Unit = {
    listener.pickCards()
  }

}
