package snakes.model

import snakes.model.Board
import snakes.model.Player
import snakes.util.Dice

import scala.collection.immutable.Queue

case class aGame(board:Board = Board(10), queue: Queue[Player] = Queue.empty) {
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

  override def toString: String =
    val stringBuilder = new StringBuilder("---------------------------\nPlayers: ")

    queue.foreach(element =>
      stringBuilder.append(element.name + "[")
      stringBuilder.append(element.position + "] ")
    )
    stringBuilder.append("\nNext Player up is: " + queue.head.name + "\n---------------------------")
    stringBuilder.toString()
}