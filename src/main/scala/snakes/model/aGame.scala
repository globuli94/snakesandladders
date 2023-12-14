package snakes.model

import snakes.model.Board
import snakes.model.Player
import snakes.util.Dice

import scala.collection.immutable.Queue

trait IGameState {
  def getBoard(): IBoard
  def getPlayers(): List[IPlayer]
  def getCurrentPlayer(): IPlayer
  def isGameStarted(): Boolean
  def startGame(): IGameState
  def movePlayer(player: IPlayer, roll: Int): IGameState
}

case class aGame(board:Board = Board.createBoard(100), queue: Queue[Player] = Queue.empty, gameStarted: Boolean = false) {

  def startGame: aGame = copy(gameStarted = true)

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

    newPosition = board.snakes.getOrElse(newPosition, newPosition)
    newPosition = board.ladders.getOrElse(newPosition, newPosition)
    newPosition = newPosition min board.size

    val updatedPlayer = player.moveTo(newPosition, roll)
    val newQueue = updatedQueue.enqueue(updatedPlayer)

    aGame(board, newQueue, gameStarted)
  }


  override def toString: String =
    if(!gameStarted && queue.isEmpty) {
      "Welcome to Snakes and Ladders" +
        "\nPlease add Players using add(PLAYER NAME) or create a new game using create(SIZE)!" +
          "\nStart the game rolling the Dice using <roll>"

    } else if(queue.last.position == 1 && !gameStarted) {
      queue.last.name + " has been added to the Game!"
    } else if(board.size == queue.last.position && gameStarted) {
      queue.last.name + " has won the game!!!"
    } else {
      val stringBuilder = new StringBuilder("---------------------------\n" + queue.last.name + "  rolled a " + queue.last.lastRoll)
      stringBuilder.append("\nPlayers: ")
      queue.foreach(element =>
        stringBuilder.append(element.name + "[")
        stringBuilder.append(element.position + "] ")
      )
      stringBuilder.append("\n" + queue.last.name + " moved to position " + queue.last.position + "!")
      stringBuilder.append("\nNext Player up is: " + queue.head.name + "\n---------------------------")
      stringBuilder.toString()
    }
}