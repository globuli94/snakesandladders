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
      val game = Game()
      val injector = Guice.createInjector(new SnakesModule)
      val controller = injector.getInstance(classOf[ControllerInterface])
      val test = controller.startGame()
      "return a game with the gameState variable set to true" in {
        controller.getCurrentGameState.isGameStarted() should be(true)
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
  }
}