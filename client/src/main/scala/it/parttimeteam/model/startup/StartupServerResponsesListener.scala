package it.parttimeteam.model.startup

import akka.actor.ActorRef

trait StartupServerResponsesListener {

  def connected(clientId: String, serverLobbyRef: ActorRef)

  def joinedToPrivateLobby()

  def joinedToPublicLobby()

  def privateLobbyCreated(privateLobbyId: String)

}
