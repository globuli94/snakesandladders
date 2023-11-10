package snakes.model
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class GameSpec extends AnyWordSpec with Matchers {

  class PredictableDice(rollOutcome: Int) extends Dice {
    override def roll(): Int = rollOutcome
  }

  "A Game" should {
    "move a player correctly based on dice roll" in {
      val predictableDice = new PredictableDice(5)
      val players = Array(Player(1), Player(2))
      val board = new Board()
      val game = new Game(players, board, predictableDice)

      val playerPos = game.movePlayer(1, predictableDice.roll())

      playerPos shouldBe 5
    }

    "handle snake correctly" in {
      val predictableDice = new PredictableDice(16)
      val players = Array(Player(2))
      val board = new Board()
      val game = new Game(players, board, predictableDice)

      val playerPos = game.movePlayer(1, predictableDice.roll())

      playerPos shouldBe board.snakes(16)
    }

    "handle ladder correctly" in {
      val predictableDice = new PredictableDice(1)
      val players = Array(Player(1))
      val board = new Board()
      val game = new Game(players, board, predictableDice)

      val playerPos = game.movePlayer(1, predictableDice.roll())

      playerPos shouldBe board.ladders(1)
    }

    "check win condition correctly" in {
      val predictableDice = new PredictableDice(4)
      val players = Array(Player(1))
      val board = new Board()
      val game = new Game(players, board, predictableDice)

      players(0) = players(0).copy(position = 96)

      game.movePlayer(1, predictableDice.roll())

      game.checkWin(1) shouldBe true
    }
  }
}
