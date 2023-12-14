package snakes
package controller

import snakes.model.{IGameState, aGame}
import snakes.util.{Dice, Event, Observable, UndoManager}

trait IGameController extends Observable {
  def startGame(): aGame
  def createGame(size: Int): Unit
  def addPlayer(name: String): Unit
  def rollDice(): Unit
  def undoLastAction(): Unit
  def getCurrentGameState: aGame
  def exitGame: Unit
}
trait ICommand {
  def doStep(): Unit
  def undoStep(): Unit
}

case class Controller(var game: aGame) extends IGameController with Observable {
  val undoManager = new UndoManager

  override def startGame(): aGame = {
    if (!game.gameStarted) {
      game = game.startGame
      notifyObservers(Event.Start)
    }
    game
  }
  override def createGame(size:Int): Unit = {
    game = game.createGame(size)
    notifyObservers(Event.Create)
  }
  override def addPlayer(name:String): Unit =
    game = game.createPlayer(name)
    notifyObservers(Event.AddPlayer)

  override def rollDice(): Unit =
    val rollResult = Dice().rollDice
    undoManager.doStep(RollCommand(this, rollResult))
    notifyObservers(Event.Roll(rollResult))

  override def undoLastAction(): Unit = {
    undoManager.undoStep()
    notifyObservers(Event.Undo)
  }

    override def getCurrentGameState: aGame ={
      game
    }

  override def exitGame: Unit = {
    sys.exit(0)
  }

  override def toString: String =
    game.toString
}