package snakes.controller

import com.google.inject.Guice
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import snakes.SnakesModule
import snakes.model.*
import snakes.model.gameComponent.Game
import snakes.model.playerComponent.Player

class ControllerSpec extends AnyWordSpec with Matchers {

  "Controller" when {
    val injector = Guice.createInjector(new SnakesModule)
    val controller = injector.getInstance(classOf[ControllerInterface])

    "undo is called on a game when a dice hase been rolled twice" should {
      val game = Game()
      val injector = Guice.createInjector(new SnakesModule)
      val controller = injector.getInstance(classOf[ControllerInterface])

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
      val injector = Guice.createInjector(new SnakesModule)
      val controller = injector.getInstance(classOf[ControllerInterface])
      controller.startGame()
      "return a game with the gameState variable set to true" in {
        controller.getCurrentGameState.isGameStarted() should be(false)
      }
    }
    "undo is called on a game when no commands have been used" should {
      val injector = Guice.createInjector(new SnakesModule)
      val controller = injector.getInstance(classOf[ControllerInterface])
      val game = controller.getCurrentGameState
      controller.undoLastAction()

      "return the same game" in {
        controller.getCurrentGameState should be(game)
      }
    }
    "toString" should {
      val injector = Guice.createInjector(new SnakesModule)
      val controller = injector.getInstance(classOf[ControllerInterface])
      val game = Game()
      val test = controller.toString
      "return the toString from the model aGame" in {
        test should be(controller.getCurrentGameState.toString)
      }
    }
    "restarting a game" should {
      "reset the game to its initial state" in {
        controller.restartGame()
        controller.getCurrentGameState.getPlayers shouldBe empty
        controller.getCurrentGameState.isGameStarted() shouldBe false
      }
    }
    "checking the win condition" should {
      "identify when a player has won the game" in {
        controller.addPlayer("Frank")
        controller.startGame()
        while (!controller.checkWin()) {
          controller.rollDice()
        }
        controller.checkWin() shouldBe true
      }
    }
    "executing a command" should {
      "change the game state accordingly" in {
        controller.restartGame()
        controller.addPlayer("Henry")
        controller.startGame()
        val initialPosition = controller.getCurrentGameState.getCurrentPlayer().getPosition
        controller.rollDice()
        controller.getCurrentGameState.getCurrentPlayer().getPosition should not be initialPosition
      }
    }
  }
}