package it.parttimeteam.view.utils

object Strings {
  var WAITING_FOR_PLAYERS = "Waiting for other players"

  var SEND = "Send"

  var CREATE_PRIVATE_CODE = "Create a private code"

  var JOIN_WITH_CODE = "Join with a code"

  var START_NEW_GAME = "Start new game"

  var INPUT_MISSING_USER_CODE_DIALOG_MESSAGE = "You must enter both username and code."

  var INPUT_MISSING_USER_NUM_DIALOG_MESSAGE = "You must enter username and select players number."

  var INPUT_MISSING_DIALOG_TITLE = "Input missing"

  var HERE_IS_YOUR_CODE = "Here is your code"

  var SELECT_PLAYERS_NUM = "Select players number"

  var USERNAME = "Username"

  var PASS_AND_DRAW_BTN = "Pass & Draw"

  var TIMER = "Timer"

  var TIME_IS_UP = "Your time is up!"

  var LEAVE_GAME_BTN = "Leave Game"

  var PASS_BTN = "Pass"

  var OTHER_PLAYERS = "Other players:"

  var GAME_LOADING_MESSAGE = "Preparing your cards..."

  var GAME_LOADING_TITLE = "Game loading"

  var RESET_BTN = "Reset"

  var SORT_RANK_BTN = "Sort Rank"

  var SORT_SUIT_BTN = "Sort Suit"

  var UPDATE_COMBINATION_BTN = "Update Combination"

  var PICK_CARDS_BTN = "Pick Cards"

  var CLEAR_SELECTION_BTN = "Clear Selection"

  var MAKE_COMBINATION_BTN = "Make Combination"

  def PLAYER_TURN_MESSAGE(playerName: String): String = s"It's $playerName turn"

  var GAME_END_PLAYER_LEFT_MESSAGE = s"The game ended. Another player left the game. Do you want to play again?"

  def GAME_LOST_ALERT_MESSAGE(winnerUsername: String): String = s"This game has a winner. And the winner is.. $winnerUsername! Do you want to play again?"

  var GAME_WON_ALERT_MESSAGE = "Congratulations! You won the game! Do you want to play again?"

  var GAME_ENDED_ALERT_TITLE = "Game ended"

  var ERROR_DIALOG_TITLE = "Error"

  var YOUR_TURN_ALERT_MESSAGE = "It's your turn"
  var TIMER_END_INFO_MESSAGE = "You haven't passed your turn. We will pass and draw a card for you."

  final var YOUR_TURN = "Your turn"
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
