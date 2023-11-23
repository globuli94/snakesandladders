import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import snakes.controller.{Controller, UndoCommand}
import snakes.model.*

class ControllerSpec extends AnyWordSpec with Matchers {

  "Controller" when {
    val game = aGame()
    val controller = Controller(game)
    val peter = Player.builder().setName("Peter").setPosition(0).build()
    "updateGame is called with a game with Peter[5] Marko[5]" should {
      val newGame = aGame()
      val queue1 = newGame.queue.enqueue(peter)

      val testGame = aGame(newGame.board, queue1)
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
    "roll" should {
      val gameWithPeter = game.createPlayer("Peter")
      val test = controller.roll
      "move the next player in the queue to the rolled position" in {
        test.queue.last.name should be("Peter")
        test.queue.last.position should(be <= 6 or be >= 1 or be(0))
      }
    }
    "toString" should {
      val test = controller.toString
      "return the toString from the model aGame" in {
        test should be(controller.game.toString)
      }
    }
    "UndoCommand is executed" should {
      "revert the game state to before the last command" in {
        controller.addPlayer("Peter")
        val preRollGame = controller.game
        controller.saveState
        controller.roll

        val postRollGame = controller.game
        postRollGame should not be preRollGame

        val undoCommand = new UndoCommand(controller)
        undoCommand.execute()
        val undoneGame = controller.game

        undoneGame should be(preRollGame)
      }
    }
  }
}