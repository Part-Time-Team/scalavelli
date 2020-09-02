package it.parttimeteam.view.game.scenes.panes

import it.parttimeteam.core.cards.Card
import it.parttimeteam.core.collections.Board
import it.parttimeteam.view.game.listeners.GameSceneListener
import it.parttimeteam.view.utils.CardUtils
import scalafx.geometry.Insets
import scalafx.scene.control.{Button, ScrollPane}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{BorderPane, HBox, VBox}

class CenterPane(listener: GameSceneListener) extends ScrollPane {
  val tableCombinations = new VBox()
  tableCombinations.spacing = 10d

  this.setContent(tableCombinations)

  def setBoard(board: Board): Unit = {
    board.combinations.zipWithIndex foreach {
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
  }

  private def pickCombinationClick(combinationId: String): Unit = {
    println(s"pickCombinationClick $combinationId")
    listener.pickCombination(combinationId)
  }
}
