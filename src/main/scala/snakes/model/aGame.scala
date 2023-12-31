package snakes.model

import snakes.model.Board
import snakes.model.Player
import snakes.util.Dice

import scala.collection.immutable.Queue

case class aGame(board:Board = Board.createBoard(100), queue: Queue[Player] = Queue.empty) {

  def createGame(size: Int): aGame = {
    aGame(Board.createBoard(size*size))
  }

  def createPlayer(name: String): aGame = {
    val player = Player.builder()
      .setName(name)
      .setPosition(1)
      .setColor(queue.size)
      .build()
    aGame(board, queue.enqueue(player))
  }

  def moveNextPlayer(roll: Int): aGame = {
    val (player, updatedQueue) = queue.dequeue
    var newPosition = player.position + roll

    newPosition = board.snakes.getOrElse(newPosition, newPosition) // If landed on snake
    newPosition = board.ladders.getOrElse(newPosition, newPosition) // If landed on ladder

    newPosition = newPosition min board.size

    val updatedPlayer = player.moveTo(newPosition)
    val newQueue = updatedQueue.enqueue(updatedPlayer)

    aGame(board, newQueue)
  }

  override def toString: String =
    if(queue.isEmpty) {
      "Welcome to Snakes and Ladders" +
        "\nPlease add Players using add(PLAYER NAME) or create a new game using create(SIZE)!" +
          "\nStart the game rolling the Dice using <roll>"
    } else if(queue.last.position == 1) {
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