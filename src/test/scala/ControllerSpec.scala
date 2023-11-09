package snakes.controller

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import snakes.model.*

class ControllerSpec extends AnyWordSpec with Matchers {

  "Controller" when {
    val game = aGame()
    val controller = Controller(game)
    "updateGame is called with a game with Peter[5] Marko[5]" should {
      val newGame = aGame()
      val queue1 = newGame.queue.enqueue(Player("Peter", 5))
      val queue2 = queue1.enqueue(Player("Marko", 5))
      val testGame = aGame(newGame.board, queue2)

      val test = controller.updateGame(testGame)
      "update the game with the new game" in {
        test should be(testGame)
      }
    }
    "addPlayer(Peter) is called on an empty game" should {
      val test = controller.addPlayer("Peter")
      "return a game with Peter added to the queue" in {
        test.queue.last.name should be("Peter")
        test.queue.last.position should be(0)
      }
    }
  }
}