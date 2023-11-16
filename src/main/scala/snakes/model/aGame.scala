package snakes.model

import snakes.model.Board
import snakes.model.Player
import snakes.util.Dice

import scala.collection.immutable.Queue
import scala.util.Random

case class aGame(board:Board = Board(10, Map(), Map()), queue: Queue[Player] = Queue.empty, lastRoll:Option[Int] = None) {
  def createPlayer(name:String): aGame =
    aGame(board, queue.enqueue(Player(name, 0)))

  private def generateSnakesAndLadders(snakesCount:Int, laddersCount:Int): (Map[Int, Int], Map[Int, Int]) = {
    val rand = new Random()
    val snakeHeads = (board.size * 2 / 3 to board.size).toList
    val ladderBottoms = (1 to board.size / 3).toList

    def generateMappings(count: Int, fromPositions: List[Int], toPositions: List[Int]): Map[Int, Int] = {
      (1 to count).foldLeft(Map[Int, Int]()) { (map, _) =>
        val from = fromPositions(rand.nextInt(fromPositions.size))
        val to = toPositions.filterNot(_ == from).head
        map + (from -> to)
      }
    }

    val snakes = generateMappings(snakesCount, snakeHeads, ladderBottoms)
    val ladders = generateMappings(laddersCount, ladderBottoms, snakeHeads)

    (snakes, ladders)
  }

  def moveNextPlayer(roll: Int): aGame = {
    val (player, newQueue) = queue.dequeue
    val newPosition = player.position + roll

    val updatedPlayer = newPosition match {
      case position if position <= board.size => player.move(roll)
      case _ => player
    }

    val maybeSnakeOrLadder = board.snakes.get(updatedPlayer.position)
      .orElse(board.ladders.get(updatedPlayer.position))

    val finalPlayer = maybeSnakeOrLadder match {
      case Some(newPosition) => updatedPlayer.copy(position = newPosition)
      case None => updatedPlayer
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
    val (snakesCount, laddersCount) = difficulty match {
      case "easy" => (1, 10)
      case "normal" => (5, 5)
      case "difficult" => (10, 1)
      case _ => (5, 5)
    }
    val size = length match {
      case "short" => 30
      case "medium" => 50
      case "long" => 100
      case _ => 10
    }
    val (snakes, ladders) = generateSnakesAndLadders(snakesCount, laddersCount)
    val newBoard = Board(size, snakes, ladders)
    val newQueue = Queue(playerNames.map(name => Player(name, 0)): _*)
    aGame(newBoard, newQueue)
  }
}