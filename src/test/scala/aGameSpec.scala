package snakes.model

import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

import scala.collection.immutable.Queue

class aGameSpec extends AnyWordSpec {
  "aGame" when {
    "initialized without parameters" should {
      val test = aGame()

      "return a game with a board the size of 10 and an empty queue" in {
        test.board.size should be(10)
        test.queue should be(Queue.empty)
      }
    }
    "initialized with parameters 20 and a queue with the players Jonathan and Peter at position 0" should {
      val game = aGame(Board(20))
      val test = game.createPlayer("Peter")
      "should return a game with the board size 20 and players Jonathan and Peter at position 0" in {
        test.board.size should be(20)
        test.queue.head.name should be("Peter")
        test.queue.head.position should be(0)
      }
    }
    "when calling moveNextPlayer(5) on a game size 10 with Peter at position 0" should {
      val game = aGame()
      val gameWithPlayer = game.createPlayer("Peter")

      val test = gameWithPlayer.moveNextPlayer(5)
      "should return a game with peter at the tail of the queue with the position 5" in {
        test.queue.last.name should be("Peter")
        test.queue.last.position should be(5)
      }
    }
    "when calling moveNextPlayer(11) on a game size 10 with Peter at position 0" should {
      val game = aGame()
      val gameWithPlayer = game.createPlayer("Peter")

      val test = gameWithPlayer.moveNextPlayer(11)
      val test2 = gameWithPlayer.queue.dequeue
      "should return a game with peter at the tail of the queue and at the position 0" in {
        test.queue.last.name should be("Peter")
        test.queue.last.position should be(0)

        test2._2 should be(Queue.empty)
      }
    }
    "when to String is called on an empty Game" should {
      val game = aGame()
      "return a string" in {
        game.toString should be("Please add Players to the Game first!")
      }
    }
    "when a player was recently added" should {
      val game = aGame()
      val test = game.createPlayer("Peter")
      "return a string .. created new player player name .." in {
        test.toString should be("Peter has been added to the Game!")
      }
    }
    "when a player has won the game" should {
      val game = aGame()
      val test = aGame(game.board, game.queue.enqueue(Player("Peter", 10)))
      "return the string -> Peter has won the Game!!!" in {
        test.toString should be("Peter has won the game!!!")
      }
    }
    "when called on a game with players" should {
      val game = aGame()
      val queue = game.queue.enqueue(Player("Peter", 3))
      val testQueue = queue.enqueue(Player("Marko", 5))

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