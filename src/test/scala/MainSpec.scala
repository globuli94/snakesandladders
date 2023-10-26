import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class MainSpec extends AnyWordSpec with Matchers {
  "" when {
    "created with Board(5)" should {
      val board = Board(5)
      "have the size" in {
        board.size should be (5)
      }
    }
  }
}