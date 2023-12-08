package snakes.controller

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import snakes.model.*

class ControllerSpec extends AnyWordSpec with Matchers {

  "Controller" when {
    val game = aGame()
    val controller = Controller(game)
    val peter = Player.builder().setName("Peter").setPosition(0).build()
    "undo is called on a game when a dice hase been rolled twice" should {
      val gameWithPlayer = controller.addPlayer("Peter")
      controller.roll()
      val test1 = controller.game
      controller.roll()
      val test2 = controller.game
      controller.undo()
      "return controller.game = test1" in {
        controller.game = test1
      }
    }
    "toString" should {
      val test = controller.toString
      "return the toString from the model aGame" in {
        test should be(controller.game.toString)
      }
    }
  }
}