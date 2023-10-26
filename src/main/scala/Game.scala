import scala.collection.mutable.ArrayBuffer

case class Game(board: Board) {
  val players: ArrayBuffer[Player] = ArrayBuffer.empty[Player]

  def addPlayer(player: Player): Unit = {
    players += player
  }
}