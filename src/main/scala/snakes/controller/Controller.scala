package snakes.controller

import com.google.inject.Inject
import snakes.model.fileIoComponent.FileIOInterface
import snakes.model.gameComponent.GameInterface
import snakes.util.{Dice, Event, Observable, UndoManager, CommandInterface}

case class Controller @Inject()(private var gameState: GameInterface, private val fileIo: FileIOInterface) extends ControllerInterface with Observable {
  private val undoManager = new UndoManager

  override def saveGame(): Unit = {
    fileIo.save(getCurrentGameState)
    notifyObservers(Event.Save)
  }

  override def loadGame(): Unit = {
    setGameState(fileIo.load)
    notifyObservers(Event.Load)
  }
  
  override def setGameState(state: GameInterface): Unit = {
    gameState = state
  }
  override def startGame(): Unit = {
    if (!gameState.isGameStarted() && gameState.getPlayers.nonEmpty) {
      gameState = gameState.startGame
      notifyObservers(Event.Start)
    }
  }
  override def restartGame(): Unit = {
    gameState = gameState.createGame(gameState.getBoard.getSize)
    notifyObservers(Event.Restart)
  }
  override def createGame(size: Int): Unit = {
    gameState = gameState.createGame(size)
    notifyObservers(Event.Create)
  }
  override def addPlayer(name: String): Unit = {
    gameState = gameState.createPlayer(name)
    notifyObservers(Event.AddPlayer)
  }
  override def rollDice(): Unit = {
    val rollResult = Dice().rollDice
    undoManager.doStep(RollCommand(this, rollResult))
    notifyObservers(Event.Roll(rollResult))
  }
  override def undoLastAction(): Unit = {
    undoManager.undoStep()
    notifyObservers(Event.Undo)
  }

  override def getCurrentGameState: GameInterface = gameState
  override def exitGame(): Unit = sys.exit(0)
  override def toString: String = gameState.toString

  def executeCommand(command: CommandInterface): Unit = {
    undoManager.doStep(command)
    notifyObservers(Event.Update)
  }

  def checkWin(): Boolean = {
    getCurrentGameState.getPlayers.exists(_.getPosition == getBoardSize)
  }

  override def getBoardSize: Int =
    gameState.getBoard.getSize
}