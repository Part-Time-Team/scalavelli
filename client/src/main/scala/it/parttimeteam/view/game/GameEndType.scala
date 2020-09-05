package it.parttimeteam.view.game

/**
  * Subtype of possible game end types
  */
sealed class GameEndType

/**
  * The player won the game.
  */
case object GameWon extends GameEndType

/**
  * Another player won the game.
  *
  * @param winnerUsername the winner player username
  */
case class GameLost(winnerUsername: String) extends GameEndType

/**
  * The game ended with an error.
  *
  * @param reason the reason why the game ended
  */
case class GameEndWithError(reason: String) extends GameEndType
