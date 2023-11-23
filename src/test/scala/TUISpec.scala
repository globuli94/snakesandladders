package snakes.aview

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import snakes.model.*
import snakes.aview.*
import snakes.controller.*
class TUISpec extends AnyWordSpec with Matchers {
  "A TUI" should {
    val game = aGame()
    val controller = Controller(game)
    val tui = TUI(controller)
    "adding a Player using add NAME" should {
      tui.getInputAndPrintLoop("add Peter")
      "add a Player with the name Peter" in {
        controller.game.queue.last.name should be("Peter")
      }
    }
    "roll" should {
      val gameWithPlayer = aGame(game.board, game.queue.enqueue(Player("Peter", 3)))
      val gameWith2Player = aGame(game.board, gameWithPlayer.queue.enqueue(Player("Lars", 2)))
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
    "any other input" should {
      tui.getInputAndPrintLoop("bla")
      "return an unchanged game" in {
        controller.game should be(controller.game)
      }
    }
  }
}
