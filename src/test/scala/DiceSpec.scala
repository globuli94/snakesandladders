package snakes.util

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class DiceSpec extends AnyWordSpec with Matchers {

  "A Dice" when {
    "rolled" should {
      "return a number between 1 and 6" in {
        val dice = new Dice()

        dice.rollDice should be >= 1
        dice.rollDice should be <= 6
      }
    }
  }
}
