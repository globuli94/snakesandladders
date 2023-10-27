import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class GameControllerSpec extends AnyWordSpec with Matchers {

  // createGame() test
  "A Game" when {
    "created with size 10" should {
      val game = GameController().createGame(10)
      "have a Board with the size 10 and a val players that is an empty Vector" in {
        game.board.size should be(10)
        game.players should be(Vector.empty)
      }
    }
  }
  
  // createBoard() test
  "A Board" when {
    "created with size 10" should {
      val board = GameController().createBoard(0)
      "have the size 0" in {
        board.size should be(0)
      }
      val board2 = GameController().createBoard(10)
      "have the size 10" in {
        board2.size should be(10)
      }
      val board3 = GameController().createBoard(100)
      "have the size 100" in {
        board3.size should be(100)
      }
    }
  }

  // createPlayer(String) test
  "A Player" when {
    "created with the name Marko" should {
      val player = GameController().createPlayer("Marko")
      "have the name" in {
        player.name should be ("Marko")
      }
      "have the position 0" in {
        player.position should be(0)
      }
    }
  }

  // movePlayer() test
  "A Player" when {
    "at the position 0 with the name Marko" should {
      val player = GameController().createPlayer("Marko")
      "move to position 3 when rolling a 3" in {
        val player_new = GameController().movePlayer(player, 3)
        player_new.position should be (3)
        player_new.name should be ("Marko")
      }
    }
  }
}
