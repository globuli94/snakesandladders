import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
import snakes.model.Player

import scala.collection.immutable.Queue

class PlayerSpec extends AnyWordSpec {
  "Player" when {
    val player = Player.builder().setName("Marko").setPosition(0).build()
    "initialized with name Marko and position 0" should {
      "return a Player with the name Jonathan and the position 0" in {
        player.name should be("Marko")
        player.position should be(0)
      }
    }
    "at position 0 and moved by moveTo(10)" should {
      val test = player.moveTo(10)
      "return a player with the position 5" in {
        test.position should be (10)
      }
    }
  }
}