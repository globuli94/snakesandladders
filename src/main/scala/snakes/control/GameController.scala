import snakes.aview.TUI
import snakes.model.Game

class GameController(game: Game, view: TUI) {
  game.addObserver(view)

  def startGame(): Unit = {
    var currentPlayerIndex = 0

    while (!game.players.indices.exists(i => game.checkWin(i + 1))) {
      val currentPlayerNumber = game.players(currentPlayerIndex).number

      println(s"Player $currentPlayerNumber's turn. Press Enter to roll the dice.")
      scala.io.StdIn.readLine()
      val roll = game.dice.roll()
      game.movePlayer(currentPlayerNumber, roll)

      currentPlayerIndex = (currentPlayerIndex + 1) % game.players.length
    }
  }
}