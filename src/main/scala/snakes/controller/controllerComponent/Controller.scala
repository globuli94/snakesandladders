package snakes.controller.controllerComponent

import com.google.inject.Inject
import snakes.controller.controllerComponent.IGameController
import snakes.model.gameComponent.{IGameState, aGame}
import snakes.util.{Dice, Event, Observable, UndoManager}




case class Controller @Inject() (private var gameState: IGameState) extends IGameController with Observable {
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