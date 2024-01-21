package snakes.aview

import com.google.inject.Guice
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import snakes.SnakesModule
import snakes.model.*
import snakes.aview.*
import snakes.controller.{Controller, *}
import snakes.model.boardComponent.Board
import snakes.model.gameComponent.Game


class TUISpec extends AnyWordSpec with Matchers {
  "A TUI" when {
    val game: Game = Game(Board.createBoard(5))
    val injector = Guice.createInjector(new SnakesModule)
    val controller = injector.getInstance(classOf[ControllerInterface])
    val tui = TUI(controller)
    "adding a Player using add NAME" should {
      tui.handleInput("add Peter")
      "add a Player with the name Peter" in {
        controller.getCurrentGameState.getPlayers.last.getName should be("Peter")
      }
    }
    "start" should {
      tui.handleInput("start")
      "set the gameStarted boolean to true" in {
        controller.getCurrentGameState.isGameStarted() should be(true)
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
    "save and load a game" should {
      "correctly save and then load the game state" in {
        tui.handleInput("save")
        tui.handleInput("add John")
        tui.handleInput("load")
        controller.getCurrentGameState.getPlayers should not contain "John"
      }
    }
    "exit the game" should {
      "terminate the game session" in {
        tui.handleInput("exit")
      }
    }
    "add multiple players" should {
      "add all the players to the game" in {
        tui.handleInput("add Alice")
        tui.handleInput("add Bob")
        controller.getCurrentGameState.getPlayers.map(_.getName) should contain allOf("Alice", "Bob")
      }
    }
    "A TUI" should {
      "handle commands with too few arguments" in {
        val initialGameState = controller.getCurrentGameState
        tui.handleInput("add")
        controller.getCurrentGameState shouldEqual initialGameState
      }
    }
  }
}
