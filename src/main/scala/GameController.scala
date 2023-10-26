import scala.io.StdIn

class GameController {
  def createBoard(size: Int): Board = {
    Board(size)
  }
  def createPlayer(name: String): Player = {
    Player(name, 0)
  }
  def movePlayer(player: Player, roll: Int): Player = {
    Player(player.name, player.position + roll)
  }
}