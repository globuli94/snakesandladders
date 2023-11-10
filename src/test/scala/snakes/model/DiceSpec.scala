package snakes.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class DiceSpec extends AnyWordSpec with Matchers {

  "A Dice" should {
    "roll a number between 1 and 6" in {
      val dice = new Dice()
      val rollResult = dice.roll()

      rollResult should be >= 1
      rollResult should be <= 6
    }
  }
}
