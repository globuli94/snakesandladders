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
      val jonathan = Player("Jonathan", 0)
      val peter = Player("Peter", 0)

      val game = aGame(Board(20))
      val game2 = game.createPlayer("Jonathan")
      val test = game.createPlayer("Peter")
      "should return a game with the board size 20 and players Jonathan and Peter at position 0" in {
        test.board.size should be(20)
        test.queue.head should be(jonathan)
        test.queue.tail should be(peter)
      }
    }
  }
}
