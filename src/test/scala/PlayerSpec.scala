import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class PlayerSpec extends AnyWordSpec with Matchers {
  "A Player" when {
    "created with Player(Marko, 5)" should {
      val player = Player("Marko", 5)
      "have the name" in {
        player.name should be ("Marko")
      }
      "have the position" in {
        player.position should be (5)
      }
    }
  }
}