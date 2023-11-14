package snakes.model

import snakes.model.Board
import snakes.model.Player
import snakes.util.Dice

import scala.collection.immutable.Queue

case class aGame(board:Board = Board(10), queue: Queue[Player] = Queue.empty) {
  def createPlayer(name:String): aGame =
    aGame(board, queue.enqueue(Player(name, 0)))

  def moveNextPlayer(roll: Int): aGame =
    val player = queue.dequeue

    player._1.position + roll match {
      case position if (position <= board.size) =>
        aGame(board, player._2.enqueue(player._1.move(roll)))
      case _ =>
        aGame(board, player._2.enqueue(player._1))
    }

  override def toString: String =
    if(queue.isEmpty) {
      "Please add Players to the Game first"
    } else if(queue.last.position == 0) {
      queue.last.name + " has been added to the Game!"
    } else if(board.size == queue.last.position) {
      queue.last.name + " has won the game!!!"
    } else {
      val stringBuilder = new StringBuilder("---------------------------\nPlayers: ")
      queue.foreach(element =>
        stringBuilder.append(element.name + "[")
        stringBuilder.append(element.position + "] ")
      )
      stringBuilder.append("\n" + queue.last.name + " moved to position " + queue.last.position + "!")
      stringBuilder.append("\nNext Player up is: " + queue.head.name + "\n---------------------------")
      stringBuilder.toString()
    }
}