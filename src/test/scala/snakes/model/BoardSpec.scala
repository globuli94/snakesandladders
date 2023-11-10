package snakes.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class BoardSpec extends AnyWordSpec with Matchers {

  "A Board" should {
    "have predefined snakes and ladders" in {
      val board = new Board()

      board.snakes shouldEqual Map(
        16 -> 6, 47 -> 26, 49 -> 11, 56 -> 53,
        62 -> 19, 64 -> 60, 87 -> 24, 93 -> 73,
        95 -> 75, 98 -> 78
      )

      board.ladders shouldEqual Map(
        1 -> 38, 4 -> 14, 9 -> 31, 21 -> 42,
        28 -> 84, 36 -> 44, 51 -> 67, 71 -> 91,
        80 -> 100
      )
    }

    "allow customization of snakes and ladders" in {
      val customSnakes = Map(5 -> 1)
      val customLadders = Map(2 -> 6)
      val board = new Board(customSnakes, customLadders)

      board.snakes shouldEqual customSnakes
      board.ladders shouldEqual customLadders
    }
  }
}
