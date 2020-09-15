package it.parttimeteam.view.utils

object Strings {
  var PASS_AND_DRAW_BTN: String = "Pass & Draw"

  var TIMER = "Timer"

  var TIME_IS_UP: String = "Your time is up!"

  var LEAVE_GAME_BTN: String = "Leave Game"

  var PASS_BTN: String = "Pass"
  
  var OTHER_PLAYERS = "Other players:"

  var GAME_LOADING_MESSAGE = "Preparing your cards..."

  var GAME_LOADING_TITLE: String = "Game loading"

  var RESET_BTN: String = "Reset"

  var SORT_RANK_BTN: String = "Sort Rank"

  var SORT_SUIT_BTN: String = "Sort Suit"

  var UPDATE_COMBINATION_BTN: String = "Update Combination"

  var PICK_CARDS_BTN: String = "Pick Cards"

  var CLEAR_SELECTION_BTN: String = "Clear Selection"

  var MAKE_COMBINATION_BTN: String = "Make Combination"

  def PLAYER_TURN_MESSAGE(playerName: String): String = s"It's $playerName turn"

  var GAME_END_PLAYER_LEFT_MESSAGE: String = s"The game ended. Another player left the game. Do you want to play again?"

  def GAME_LOST_ALERT_MESSAGE(winnerUsername: String): String = s"This game has a winner. And the winner is.. $winnerUsername! Do you want to play again?"

  var GAME_WON_ALERT_MESSAGE: String = "Congratulations! You won the game! Do you want to play again?"

  var GAME_ENDED_ALERT_TITLE: String = "Game ended"

  var ERROR_DIALOG_TITLE: String = "Error"

  var YOUR_TURN_ALERT_MESSAGE = "It's your turn"
  var TIMER_END_INFO_MESSAGE: String = "You haven't passed your turn. We will pass and draw a card for you."

  final var YOUR_TURN: String = "Your turn"
  final var LEAVE_GAME_DIALOG_MESSAGE = "Are you sure you want to leave the game? The action cannot be undone."
  final var LEAVE_GAME_DIALOG_TITLE = "Leave the game"

  object Error {
    final val SERVER_NOT_FOUND = "Server not found"
    final val COMBINATION_NOT_VALID = "Invalid combination"
    final val CARD_NOT_CONTAINED_IN_HAND = "Hand doesn't contains a card you selected"
    final val NO_CARD_IN_BOARD = "Board doesn't contains a card you selected"
    final val UNEXPECTED_ERROR = "An unexpected error occurred"
    final val NOT_VALID_PLAY = "Invalid play in this turn."
    final val LOBBY_CODE_NOT_VALID = "The code is not valid"
  }

}
