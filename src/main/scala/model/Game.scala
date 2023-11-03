package model
import scala.collection.immutable.Queue
case class Game(board:Board, queue: Queue[Player] = Queue.empty) {
  def createPlayer(name:String): Game = {
    Game(board, queue.enqueue(Player(name, 0)))
  }
}