package snakes.model.fileIoComponent.fileIoJsonImpl

import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import snakes.model.gameComponent.{Game, GameInterface}
import snakes.model.playerComponent.Player

import scala.collection.immutable.Queue
import java.awt.Color
import snakes.model.boardComponent.Board

import java.io.{File, PrintWriter}

class FileIOJsonSpec extends AnyWordSpec with Matchers {
  "A FileIO JSON" when {
    val fileIO = new FileIO()

    "saving a game" should {
      val boardSize = 10
      val player1 = Player("Alice", 1, new Color(255, 0, 0), 3)
      val player2 = Player("Bob", 2, new Color(0, 255, 0), 6)
      val game = Game(Board.createBoard(boardSize), Queue(player1, player2), true)

      "write the game to a JSON file" in {
        fileIO.save(game)
        val jsonString = scala.io.Source.fromFile("game.json").getLines.mkString
        jsonString should not be empty
      }
    }

    "loading a game" should {
      "load a game from a valid JSON file" in {
        val game: GameInterface = fileIO.load
        game shouldBe a[GameInterface]
        game.getBoard.getSize should be(10)
        game.getPlayers should have size 2
        game.isGameStarted() should be(true)
      }

      "throw an exception when loading from an invalid JSON file" in {
        new PrintWriter("game.json") { write("{invalid json}"); close() }

        a[Exception] should be thrownBy {
          fileIO.load
        }
      }
    }
  }
}
