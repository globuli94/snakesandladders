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
    "when calling moveNextPlayer on a game with Peter at position 0" should {
      val test = aGame()
      
    }
  }
}
