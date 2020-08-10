package it.parttimeteam.model.startup

/**
 * Exposes all the startup/lobby functionality
 */
trait StartupService {

  /**
   * Ask the server for a connection
   *
   * @param address server address
   * @param port    server port
   */
  def connect(address: String, port: Int): Unit

  /**
   *
   * @param username        name chosen by the user
   * @param numberOfPlayers number of players for the match
   */
  def joinPublicLobby(username: String, numberOfPlayers: Int)

  /**
   *
   * @param username        name chosen by the user
   * @param numberOfPlayers number of players for the match
   */
  def createPrivateLobby(username: String, numberOfPlayers: Int)

  /**
   *
   * @param username       name chosen by the user
   * @param privateLobbyId private lobby id the user wants to join
   */
  def joinPrivateLobby(username: String, privateLobbyId: String)

  def leaveLobby()

}
