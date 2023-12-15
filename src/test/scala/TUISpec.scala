package snakes.aview

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import snakes.model.*
import snakes.aview.*
import snakes.controller.*
import snakes.controller.controllerComponent.Controller
import snakes.model.boardComponent.Board
import snakes.model.gameComponent.aGame
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
    "create" should {
      "create a game with a board of size 10 when 'create 10' is input" in {
        tui.getInputAndPrintLoop("create 10")
        controller.game.board.size shouldBe 100
      }
    }
    "undo" should {
      "undo the last operation and call controller.undo" in {
        val gameWithPlayer = controller.addPlayer("Peter")
        controller.roll()
        val test1 = controller.game
        controller.roll()
        val test2 = controller.game
        tui.getInputAndPrintLoop("undo")
        controller.game should be (test1)
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
