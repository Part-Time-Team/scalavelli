package it.parttimeteam.view.utils

object Strings {

  final val CLEAR_SELECTION_BTN = "Clear Selection"
  final val CLOSE = "Close"
  final val CREATE_PRIVATE_CODE = "Create a private code"
  final val ERROR_DIALOG_TITLE = "Error"
  final val GAME_ENDED_ALERT_TITLE = "Game ended"
  final val GAME_END_PLAYER_LEFT_MESSAGE = s"The game ended. Another player left the game. Do you want to play again?"
  final val GAME_LOADING_MESSAGE = "Preparing your cards..."
  final val GAME_LOADING_TITLE = "Game loading"
  final val GAME_WON_ALERT_MESSAGE = "Congratulations! You won the game! Do you want to play again?"
  final val HERE_IS_YOUR_CODE = "Here is your code"
  final val INPUT_MISSING_DIALOG_TITLE = "Input missing"
  final val INPUT_MISSING_USER_CODE_DIALOG_MESSAGE = "You must enter both username and code."
  final val INPUT_MISSING_USER_NUM_DIALOG_MESSAGE = "You must enter username and select players number."
  final val JOIN_WITH_CODE = "Join with a code"
  final val LEAVE_GAME_BTN = "Leave Game"
  final val LEAVE_GAME_DIALOG_MESSAGE = "Are you sure you want to leave the game? The action cannot be undone."
  final val LEAVE_GAME_DIALOG_TITLE = "Leave the game"
  final val MAKE_COMBINATION_BTN = "Make Combination"
  final val OTHER_PLAYERS = "Other players:"
  final val PASS_AND_DRAW_BTN = "Pass & Draw"
  final val PASS_BTN = "Pass"
  final val PICK_CARDS_BTN = "Pick Cards"
  final val RESET_BTN = "Reset"
  final val RETRY = "Retry"
  final val SELECT_PLAYERS_NUM = "Select players number"
  final val SEND = "Send"
  final val SORT_RANK_BTN = "Sort Rank"
  final val SORT_SUIT_BTN = "Sort Suit"
  final val START_NEW_GAME = "Start new game"
  final val TIMER = "Timer"
  final val TIMER_END_INFO_MESSAGE = "You haven't passed your turn. We will pass and draw a card for you."
  final val TIME_IS_UP = "Your time is up!"
  final val UPDATE_COMBINATION_BTN = "Update Combination"
  final val USERNAME = "Username"
  final val WAITING_FOR_PLAYERS = "Waiting for other players"
  final val YOUR_TURN = "Your turn"
  final val YOUR_TURN_ALERT_MESSAGE = "It's your turn"

  final def GAME_LOST_ALERT_MESSAGE(winnerUsername: String): String = s"This game has a winner. And the winner is.. $winnerUsername! Do you want to play again?"

  final def PLAYER_TURN_MESSAGE(playerName: String): String = s"It's $playerName turn"

  object Error {
    final val CARD_NOT_CONTAINED_IN_HAND = "Hand doesn't contains a card you selected."
    final val COMBINATION_NOT_VALID = "Invalid combination."
    final val LOBBY_CODE_NOT_VALID = "The code is not valid."
    final val NOT_VALID_PLAY = "Invalid play in this turn."
    final val NO_CARD_IN_BOARD = "Board doesn't contains a card you selected."
    final val SERVER_NOT_FOUND = "Server not found."
    final val UNEXPECTED_ERROR = "An unexpected error occurred."
  }

}
