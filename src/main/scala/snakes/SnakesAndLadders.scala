import snakes.aview.TUI
import snakes.model.{Board, Dice, Game, Player}

object SnakesAndLaddersLauncher {
  def main(args: Array[String]): Unit = {
    val playerCount = scala.io.StdIn.readLine("Enter the number of players: ").toInt
    val players = Array.tabulate(playerCount)(n => Player(n + 1))
    val board = Board()
    val dice = Dice()
    val game = new Game(players, board, dice)
    val view = new TUI()
    val controller = new GameController(game, view)

    controller.startGame()
  }
}