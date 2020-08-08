package it.parttimeteam.model.startup

import akka.actor.ActorRef

trait StartupServerResponsesListener {

  def connected(clientId: String, serverLobbyRef: ActorRef)

  def addedToLobby()

  def privateLobbyCreated(privateLobbyId: String)

  def matchFound(matchRef: ActorRef)

  // TODO error

}
