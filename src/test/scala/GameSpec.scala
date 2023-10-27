import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class GameSpec extends AnyWordSpec with Matchers {
  "A Board" when {
    "created with Board(5)" should {
      val board = Board(5)
      "have the size" in {
        board.size should be (5)
      }
    }
  }
}