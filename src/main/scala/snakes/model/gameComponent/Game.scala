package snakes.model.gameComponent
import com.google.inject.Inject
import snakes.model.*
import snakes.model.boardComponent.{Board, BoardInterface}
import snakes.model.playerComponent.{PlayerInterface, Player}
import scala.collection.immutable.Queue



case class Game @Inject()(board: Board = Board.createBoard(100),
                          queue: Queue[PlayerInterface] = Queue.empty,
                          gameStarted: Boolean = false)
  extends GameInterface {

  override def getBoard: BoardInterface = board
  override def getPlayers: Queue[PlayerInterface] = queue
  override def getCurrentPlayer(): PlayerInterface = getPlayers.head
  override def isGameStarted(): Boolean = gameStarted
  override def startGame: Game = copy(gameStarted = true)
  
  override def createGame(size: Int): Game = {
    Game(Board.createBoard(size*size))
  }
  override def createPlayer(name: String): Game = {
    val player = Player.builder()
      .setName(name)
      .setPosition(1)
      .setColor(queue.size)
      .build()
    Game(board, queue.enqueue(player))
  }
  override def moveNextPlayer(roll: Int): Game = {
    val (player, updatedQueue) = queue.dequeue
    var newPosition = player.getPosition + roll

    newPosition = board.snakes.getOrElse(newPosition, newPosition)
    newPosition = board.ladders.getOrElse(newPosition, newPosition)
    newPosition = newPosition min board.size

    val updatedPlayer = player.moveTo(newPosition, roll)
    val newQueue = updatedQueue.enqueue(updatedPlayer)

    Game(board, newQueue, gameStarted)
  }
  
  override def toString: String =
    if(!gameStarted && queue.isEmpty) {
      "Welcome to Snakes and Ladders" +
        "\nPlease add Players using add(PLAYER NAME) or create a new game using create(SIZE)!" +
          "\nStart the game rolling the Dice using <roll>"
    } else if(queue.last.getPosition == 1 && !gameStarted) {
      queue.last.getName + " has been added to the Game!"
    } else if(board.size == queue.last.getPosition) {
      queue.last.getName + " has won the game!!!"
    } else {
      val stringBuilder = new StringBuilder("---------------------------\n" + queue.last.getName + " rolled a " + queue.last.getLastRoll)
      stringBuilder.append("\nPlayers: ")
      queue.foreach(element =>
        stringBuilder.append(element.getName + "[")
        stringBuilder.append(element.getPosition + "] ")
      )
      stringBuilder.append("\n" + queue.last.getName + " moved to position " + queue.last.getPosition + "!")
      stringBuilder.append("\nNext Player up is: " + queue.head.getName + "\n---------------------------")
      stringBuilder.toString()
    }
}