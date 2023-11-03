package snakes
package model
import scala.collection.immutable.Queue
import util.Dice

case class aGame(board:Board = model.Board(10), queue: Queue[Player] = Queue.empty) {
  def createPlayer(name:String): aGame =
    aGame(board, queue.enqueue(Player(name, 0)))

  def moveNextPlayer: aGame =
    val player = queue.dequeue
    val diceRoll = Dice().rollDice

    player._1.position + diceRoll match {
      case position if (position <= board.size) =>
        aGame(board, player._2.enqueue(player._1.move(diceRoll)))
      case _ =>
        aGame(board, player._2.enqueue(player._1))
    }
}