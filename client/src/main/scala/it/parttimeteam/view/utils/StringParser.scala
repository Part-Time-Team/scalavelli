package it.parttimeteam.view.utils

import it.parttimeteam.controller.ViewMessage
import it.parttimeteam.controller.ViewMessage._
import it.parttimeteam.model.ErrorEvent
import it.parttimeteam.model.ErrorEvent._

object StringParser {

  def parseError(error: ErrorEvent): String = error match {

    case GenericError(reason: String) => reason

    case ServerNotFound => Strings.Error.SERVER_NOT_FOUND

    case NoValidTurnPlay => Strings.Error.NOT_VALID_PLAY

    case CombinationNotValid => Strings.Error.COMBINATION_NOT_VALID

    case HandNotContainCard => Strings.Error.CARD_NOT_CONTAINED_IN_HAND

    case NoCardInBoard => Strings.Error.NO_CARD_IN_BOARD

    case LobbyCodeNotValid => Strings.Error.LOBBY_CODE_NOT_VALID

    case _ => Strings.Error.UNEXPECTED_ERROR
  }

  def parseMessage(message: ViewMessage): String = message match {

    case ActualPlayerTurn(playerName: String) => Strings.PLAYER_TURN_MESSAGE(playerName)

    case YourTurn => Strings.YOUR_TURN
  }
}
