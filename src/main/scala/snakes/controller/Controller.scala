package snakes
package controller

import snakes.model.{IGameState, aGame}
import snakes.util.{Dice, Event, Observable, UndoManager}

trait IGameController extends Observable {
  def startGame(): Unit
  def createGame(size: Int): Unit
  def addPlayer(name: String): Unit
  def rollDice(): Unit
  def undoLastAction(): Unit
  def getCurrentGameState: IGameState
  def exitGame(): Unit
  def setGameState(state: IGameState): Unit
}
trait ICommand {
  def doStep(): Unit
  def undoStep(): Unit
}

case class Controller(private var gameState: IGameState) extends IGameController with Observable {
  val undoManager = new UndoManager

  override def setGameState(state: IGameState): Unit = {
    gameState = state
  }

  override def startGame(): Unit = {
    if (!gameState.isGameStarted()) {
      gameState = gameState.startGame
      notifyObservers(Event.Start)
    }
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

  override def getCurrentGameState: IGameState = gameState


  override def exitGame(): Unit = {
    sys.exit(0)
  }

  override def toString: String = gameState.toString
}