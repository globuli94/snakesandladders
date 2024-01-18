package snakes.model

import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
import snakes.model.boardComponent.Board
import snakes.model.gameComponent.Game
import snakes.model.playerComponent.Player

import scala.collection.immutable.Queue

class aGameSpec extends AnyWordSpec {
  "aGame" when {
    "initialized without parameters" should {
      val test = Game()

      "return a game with a board the size of 10 and an empty queue" in {
        test.board.size should be(100)
        test.queue should be(Queue.empty)
      }
    }
    "when a player was recently added" should {
      val game = Game()
      val test = game.createPlayer("Peter")
      "return a string .. created new player player name .." in {
        test.toString should be("Peter has been added to the Game!")
      }
    }
    "when a player has won the game" should {
      val game = Game().createPlayer("Peter").moveNextPlayer(99)
      "return a string" in {
        game.toString should be("Peter has won the game!!!")
      }
    }
    "when calling getCurrentPlayer()" should {
      val game = Game()
      val test = game.createPlayer("Peter")
      "return the player Peter" in {
        test.getCurrentPlayer().getName should be("Peter")
      }
    }
    "when calling isGameStarted()" should {
      val test = Game()
      "return false" in {
        test.isGameStarted() should be (false)
      }
    }
    "when calling startGame" should {
      val game = Game()
      val test = game.startGame
      "return a game where isGameStarted is true" in {
        test.isGameStarted() should be(true)
      }
    }
    "when called on a game with players" should {
      val game = Game()
      val playerPeter = Player.builder().setName("Peter").setPosition(3).build()
      val playerMarko = Player.builder().setName("Marko").setPosition(5).build()
      val queue = game.queue.enqueue(playerPeter)
      val testQueue = queue.enqueue(playerMarko)

      val test = Game(game.board, testQueue)
      println(test.toString)

      "return a String -> ---------------------------Players: " in {
        test.toString should be(
          "---------------------------\n"
            +"Marko rolled a 0\nPlayers: Peter[3] Marko[5] "
          + "\nMarko moved to position 5!"
          + "\nNext Player up is: Peter"
          + "\n---------------------------")
      }
    }
  }
}