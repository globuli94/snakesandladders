package snakes.model

import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

import scala.collection.immutable.Queue

class PlayerSpec extends AnyWordSpec {
  "Player" when {
    "initialized with name Jonathan and position 10" should {
      val test = Player("Jonathan", 0)

      "return a Player with the name Jonathan and the position 0" in {
        test.name should be("Jonathan")
        test.position should be(0)
      }
    }
    "at position 0 and moved by 5" should {
      val player = Player("Marko", 0)
      val test = player.move(5)
      "should return a Player with the name Marko and the position 5" in {
        test.name should be ("Marko")
        test.position should be(5)
      }
    }
  }
}