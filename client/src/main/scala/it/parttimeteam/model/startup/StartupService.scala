package it.parttimeteam.model.startup

trait StartupService {

  def connect(address: String, port: Int): Unit

  def joinPublicLobby(username: String, numberOfPlayers: Int)

  def createPrivateLobby(username: String, numberOfPlayers: Int)

  def joinPrivateLobby(username: String, privateLobbyId: String)

  def leaveLobby()


}
