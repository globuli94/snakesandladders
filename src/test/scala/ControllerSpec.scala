package snakes.controller

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import snakes.controller.controllerComponent.Controller
import snakes.model.*
import snakes.model.gameComponent.aGame
import snakes.model.playerComponent.Player

class ControllerSpec extends AnyWordSpec with Matchers {

  "Controller" when {
    val game = aGame()
    val controller = Controller(game)
    val peter = Player.builder().setName("Peter").setPosition(0).build()
    "undo is called on a game when a dice hase been rolled twice" should {
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
    "toString" should {
      val test = controller.toString
      "return the toString from the model aGame" in {
        test should be(controller.getCurrentGameState.toString)
      }
    }
  }
}