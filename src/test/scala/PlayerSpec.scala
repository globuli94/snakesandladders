package snakes.model

import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
import snakes.model.playerComponent.Player
import java.awt.Color


import scala.collection.immutable.Queue

class PlayerSpec extends AnyWordSpec {
  "Player" when {
    val player = Player.builder().setName("Marko").setPosition(0).setColor(1).build()
    "initialized with name Marko and position 0" should {
      "return a Player with the name Jonathan and the position 0" in {
        player.name should be("Marko")
        player.position should be(0)
        player.getColor should be(Color.black)
      }
    }
    "at position 0 and moved by moveTo(10)" should {
      val test = player.moveTo(10, 0)
      "return a player with the position 5" in {
        test.getPosition should be (10)
        test.getLastRoll should be(0)
      }
    }
  }
}