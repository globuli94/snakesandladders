package snakes.model

import snakes.model.Board
import snakes.model.Player
import snakes.util.Dice

import scala.collection.immutable.Queue

case class aGame(board:Board = Board.createBoard(10), queue: Queue[Player] = Queue.empty, gameStarted: Boolean = false) {

  def startGame: aGame = copy(gameStarted = true)


  def saveToMemento: GameMemento = GameMemento(board, queue)

  def restoreFromMemento(memento: GameMemento): aGame = {
    aGame(memento.board, memento.queue)
  }

  def createGame(size: Int): aGame = {
    aGame(Board.createBoard(size*size))
  }

  def createPlayer(name: String): aGame = {
    val player = Player.builder()
      .setName(name)
      .setPosition(0)
      .build()
    aGame(board, queue.enqueue(player))
  }

  def moveNextPlayer(roll: Int): aGame = {
    val (player, updatedQueue) = queue.dequeue
    var newPosition = player.position + roll
    // Check if the new position hits a snake or ladder
    val originalPosition = newPosition
    newPosition = board.snakes.getOrElse(newPosition, newPosition) // If landed on snake
    if (newPosition < originalPosition) {
      println(s"Oh no! ${player.name} hit a snake and moved from $originalPosition to $newPosition.")
    }
    newPosition = board.ladders.getOrElse(newPosition, newPosition) // If landed on ladder
    if (newPosition > originalPosition) {
      println(s"Great! ${player.name} climbed a ladder and moved from $originalPosition to $newPosition.")
    }
    newPosition = newPosition min board.size
    val updatedPlayer = player.moveTo(newPosition)
    val newQueue = updatedQueue.enqueue(updatedPlayer)
    aGame(board, newQueue)
  }


  override def toString: String =
    if (gameStarted) {
    "game started, please roll the dice."
    }
    else if(queue.isEmpty) {
      "Please add Players:"
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