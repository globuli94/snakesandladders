import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class SnakesAndLaddersSpec extends AnyWordSpec with Matchers {

    "Main function" when {
      "called" should {
        "return Unit" in {
          val returnValue: Unit = SnakesAndLadders.main(Array.empty)
          assert(returnValue === ())
        }
      }
    }
}
