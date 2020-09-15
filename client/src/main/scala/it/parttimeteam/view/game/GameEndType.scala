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
  * The game ended. Another player left the game.
  */
case object GameEndPlayerLeft extends GameEndType
