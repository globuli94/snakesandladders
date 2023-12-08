package snakes.model

import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

import scala.collection.immutable.Queue

class aGameSpec extends AnyWordSpec {
  "aGame" when {
    "initialized without parameters" should {
      val test = aGame()

      "return a game with a board the size of 10 and an empty queue" in {
        test.board.size should be(100)
        test.queue should be(Queue.empty)
      }
    }
    "when calling moveNextPlayer(5) on a game size 10 with Peter at position 0" should {
      val game = aGame(Board.createBoard(10))
      val gameWithPlayer = game.createPlayer("Peter")

      val test = gameWithPlayer.moveNextPlayer(6)
      "should return a game with peter at the tail of the queue with the position 5" in {
        test.queue.last.name should be("Peter")
        test.queue.last.position should be(7)
      }
    }
    "when calling moveNextPlayer(11) on a game size 2 with Peter at position 0" should {
      val game = aGame(Board.createBoard(2))
      val gameWithPlayer = game.createPlayer("Peter")

      val test = gameWithPlayer.moveNextPlayer(11)
      val test2 = gameWithPlayer.queue.dequeue
    }
    "when calling moveNextPlayer() on a game size 5 and a ladder on field 1" should {
      val game = aGame().createGame(5)
      val gameWithPlayer = game.createPlayer("Peter")

      val test = gameWithPlayer.moveNextPlayer(1)
    }
    "when a player was recently added" should {
      val game = aGame()
      val test = game.createPlayer("Peter")
      "return a string .. created new player player name .." in {
        test.toString should be("Peter has been added to the Game!")
      }
    }
    "when a player has won the game" should {
      val game = aGame().createPlayer("Peter").moveNextPlayer(99)
      "return a string" in {
        game.toString should be("Peter has won the game!!!")
      }
    }
    "when called on a game with players" should {
      val game = aGame()
      val playerPeter = Player.builder().setName("Peter").setPosition(3).build()
      val playerMarko = Player.builder().setName("Marko").setPosition(5).build()
      val queue = game.queue.enqueue(playerPeter)
      val testQueue = queue.enqueue(playerMarko)

      val test = aGame(game.board, testQueue)
      "return a String -> ---------------------------nPlayers: " in {
        test.toString should be("---------------------------\nPlayers: Peter[3] Marko[5] "
          + "\nMarko moved to position 5!"
          + "\nNext Player up is: Peter"
          + "\n---------------------------")
      }
    }
  }
}