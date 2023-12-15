package snakes.controller

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import snakes.controller.controllerComponent.Controller
import snakes.model.*
import snakes.model.gameComponent.aGame
import snakes.model.playerComponent.Player

class ControllerSpec extends AnyWordSpec with Matchers {

  "Controller" when {
    "undo is called on a game when a dice hase been rolled twice" should {
      val game = aGame()
      val controller = Controller(game)

      val gameWithPlayer = controller.addPlayer("Peter")
      controller.rollDice()
      val test1 = controller.getCurrentGameState
      controller.rollDice()
      val test2 = controller.getCurrentGameState
      controller.undoLastAction()
      "return controller.game = test1" in {
        controller.getCurrentGameState == test1
      }
    }
    "when calling startGame() on a base game" should {
      val game = aGame()
      val controller = Controller(game)

      val test = controller.startGame()
      "return a game with the gameState variable set to true" in {
        controller.getCurrentGameState.isGameStarted() should be(true)
      }
    }
    "undo is called on a game when no commands have been used" should {
      val game = aGame()
      val controller = Controller(game)

      controller.undoLastAction()

      "return the same game" in {
        controller.getCurrentGameState should be(game)
      }
    }
    "toString" should {
      val game = aGame()
      val controller = Controller(game)
      val test = controller.toString
      "return the toString from the model aGame" in {
        test should be(controller.getCurrentGameState.toString)
      }
    }
  }
}