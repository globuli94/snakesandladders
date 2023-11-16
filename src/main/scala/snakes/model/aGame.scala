package snakes.model

import snakes.model.Board
import snakes.model.Player
import snakes.util.Dice

import scala.collection.immutable.Queue

case class aGame(board:Board = Board(10, 0, 0), queue: Queue[Player] = Queue.empty, lastRoll:Option[Int] = None) {
  def createPlayer(name:String): aGame =
    aGame(board, queue.enqueue(Player(name, 0)))

  def moveNextPlayer(roll: Int): aGame = {
    val (player, newQueue) = queue.dequeue
    val newPosition = player.position + roll

    val updatedPlayer = newPosition match {
      case position if position <= board.size => player.move(roll)
      case _ => player
    }

    aGame(board, newQueue.enqueue(updatedPlayer), Some(roll))
  }

  override def toString: String =
    if(queue.isEmpty) {
      "Please add Players to the Game first!"
    } else if(queue.last.position == 0) {
      queue.last.name + " has been added to the Game!"
    } else if(board.size == queue.last.position) {

      queue.last.name + " has won the game!!!"
    } else {
      val stringBuilder = new StringBuilder("---------------------------\n")
      lastRoll match {
        case Some(roll) => stringBuilder.append("you just rolled a " + roll+ "\n")
        case None => stringBuilder.append("No roll yet\n")
      }
      stringBuilder.append("Players: ")
      queue.foreach(element =>
        stringBuilder.append(element.name + "[")
        stringBuilder.append(element.position + "] ")
      )
      stringBuilder.append("\n" + queue.last.name + " moved to position " + queue.last.position + "!")
      stringBuilder.append("\nNext Player up is: " + queue.head.name + "\n---------------------------")
      stringBuilder.toString()
    }

  def setupGame(length: String, difficulty: String, playerNames: List[String]): aGame = {
    val (snakes, ladders) = difficulty match {
      case "easy" => (1, 5)
      case "normal" => (3, 2)
      case "difficult" => (5, 1)
      case _ => (0, 0)
    }
    val size = length match {
      case "short" => 30
      case "medium" => 50
      case "long" => 100
      case _ => 10
    }
    val newBoard = Board(size, snakes, ladders)
    val newQueue = Queue(playerNames.map(name => Player(name, 0)): _*)
    aGame(newBoard, newQueue)
  }
}