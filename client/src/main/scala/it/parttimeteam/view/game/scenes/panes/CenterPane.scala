package it.parttimeteam.view.game.scenes.panes

import it.parttimeteam.core.cards.Card
import it.parttimeteam.core.collections.{Board, CardCombination}
import it.parttimeteam.view.ViewConfig
import it.parttimeteam.view.game.PlayerCard
import it.parttimeteam.view.game.listeners.GameSceneToStageListener
import it.parttimeteam.view.game.scenes.BoardListener
import scalafx.geometry.Insets
import scalafx.scene.control.{Button, ScrollPane}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{BorderPane, HBox, VBox}

class CenterPane(listener: GameSceneToStageListener, boardListener: BoardListener) extends ScrollPane {
  val tableCombinations = new VBox()
  tableCombinations.spacing = 10d
  tableCombinations.padding = Insets(ViewConfig.CARD_Y_TRANSLATION, ViewConfig.screenPadding, ViewConfig.screenPadding, ViewConfig.screenPadding)

  var selectedCards: Seq[Card] = Seq()

  this.setContent(tableCombinations)

  def setBoard(board: Board): Unit = {
    for (combination: CardCombination <- board.combinations) {
      val combinationContainer = new BorderPane()

      combinationContainer.setPadding(Insets(10d))

      val combinationCards = new HBox()
      combinationCards.spacing = 10d

      val pickBtn = new Button()
      val img = new ImageView(new Image("images/pick.png"))
      img.fitHeight = 15d
      img.preserveRatio = true

      pickBtn.setGraphic(img)
      pickBtn.onAction = _ => pickCombinationClick(combination.id)

      pickBtn.margin = Insets(0, 0, 0, 5)

      combinationContainer.left = combinationCards
      combinationContainer.right = pickBtn
      combinationContainer.getStyleClass.add("combination")

      for (card: Card <- combination.cards) {
        val playerCard: PlayerCard = PlayerCard(card)

        combinationCards.children.add(playerCard)
        playerCard.setOnMouseClicked(_ => boardListener.onBoardCardClicked(playerCard))
      }

      tableCombinations.children.add(combinationContainer)
    }
  }

  private def pickCombinationClick(combinationId: String): Unit = {
    listener.pickCombination(combinationId)
  }
}
