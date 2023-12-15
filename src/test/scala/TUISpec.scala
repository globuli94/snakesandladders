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
      tui.handleInput("add Peter")
      "add a Player with the name Peter" in {
        controller.getCurrentGameState.getPlayers.last.getName should be("Peter")
      }
    }
    "roll" should {
      tui.handleInput("roll")
      "return a game with the updated position of the next player" in {
        controller.getCurrentGameState.getPlayers.last.getName should be("Peter")
      }
    }
    "create" should {
      "create a game with a board of size 10 when 'create 10' is input" in {
        tui.handleInput("create 10")
        controller.getCurrentGameState.getBoard.getSize shouldBe 100
      }
    }
    "undo" should {
      "undo the last operation and call controller.undo" in {
        val gameWithPlayer = controller.addPlayer("Peter")
        controller.rollDice()
        val test1 = controller.getCurrentGameState
        controller.rollDice()
        val test2 = controller.getCurrentGameState
        tui.handleInput("undo")
        controller.getCurrentGameState should be (test1)
      }
    }
    "any other input" should {
      val game: aGame = aGame(Board.createBoard(5))
      val controller: Controller = Controller(game)
      val tui = TUI(controller)
      tui.handleInput("bla")
      "return an unchanged game" in {
        controller.getCurrentGameState should be(game)
      }
    }

  }
}
