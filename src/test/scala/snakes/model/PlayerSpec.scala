package snakes.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class PlayerSpec extends AnyWordSpec with Matchers {

  "A Player" should {
    "have a correct number and initial position" in {
      val playerNumber = 5
      val player = new Player(playerNumber)

      player.number shouldEqual playerNumber
      player.position shouldEqual 0
    }

    "have a correct number and specified position" in {
      val playerNumber = 3
      val playerPosition = 10
      val player = new Player(playerNumber, playerPosition)

      player.number shouldEqual playerNumber
      player.position shouldEqual playerPosition
    }
  }
}
