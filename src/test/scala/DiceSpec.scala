import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class DiceSpec extends AnyWordSpec with Matchers {
  "Dice" should {
    "always roll a dice to produce a number between 1 and 6" in {
      val diceRolls = for (_ <- 1 to 1000) yield Dice().rollDice()
      all(diceRolls) should be >= 1
      all(diceRolls) should be <= 6
    }
  }
}