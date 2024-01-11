package snakes

import com.google.inject.Guice
import snakes.SnakesModule
import snakes.model.fileIoComponent.FileIOInterface
import snakes.model.gameComponent.Game
import snakes.model.playerComponent.Player

object FileIOTest extends App {
  // Creating a Guice injector
  val injector = Guice.createInjector(new SnakesModule)
  // Using the injector to get an instance of FileIOInterface
  val fileIO = injector.getInstance(classOf[FileIOInterface])

  // Create a test game state
  val player1 = Player("Alice", 1, new java.awt.Color(255, 0, 0), 0)
  val player2 = Player("Bob", 2, new java.awt.Color(0, 255, 0), 0)
  val testGame = Game().createPlayer(player1.getName).createPlayer(player2.getName)

  // Save
  fileIO.save(testGame)

  // Load
  val loadedGame = fileIO.load

  // Compare original and loaded game states
  println("Original Game State:")
  testGame.getPlayers.foreach(player => println(s"${player.getName}: ${player.getPosition}"))

  println("\nLoaded Game State:")
  loadedGame.getPlayers.foreach(player => println(s"${player.getName}: ${player.getPosition}"))

}
