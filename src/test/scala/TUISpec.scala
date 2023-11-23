import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import snakes.model.*
import snakes.aview.*
import snakes.controller.*
class TUISpec extends AnyWordSpec with Matchers {
  "A TUI" when {
    val game: aGame = aGame(Board.createBoard(5))
    val controller: Controller = Controller(game)
    val tui = TUI(controller)
    "adding a Player using add NAME" should {
      tui.getInputAndPrintLoop("add Peter")
      "add a Player with the name Peter" in {
        controller.game.queue.last.name should be("Peter")
      }
    }
    "roll" should {
      tui.getInputAndPrintLoop("roll")
      "return a game with the updated position of the next player" in {
        controller.game.queue.last.name should be("Peter")
      }
    }
    "undo is used after a roll" should {
      "revert the game state to before the roll" in {
        tui.getInputAndPrintLoop("add Peter")
        val preRollGame = controller.game
        tui.getInputAndPrintLoop("roll")

        val postRollGame = controller.game
        postRollGame should not be preRollGame
        tui.getInputAndPrintLoop("undo")
        val undoneGame = controller.game

        undoneGame should be(preRollGame)
      }
    }

    "create" should {
      "create a game with a board of size 10 when 'create 10' is input" in {
        tui.getInputAndPrintLoop("create 10")
        controller.game.board.size shouldBe 100
      }
    }
    "any other input" should {
      val game: aGame = aGame(Board.createBoard(5))
      val controller: Controller = Controller(game)
      val tui = TUI(controller)
      tui.getInputAndPrintLoop("bla")
      "return an unchanged game" in {
        controller.game should be(controller.game)
      }
    }
  }
}