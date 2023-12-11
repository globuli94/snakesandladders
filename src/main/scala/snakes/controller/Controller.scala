package snakes
package controller

import snakes.model.aGame
import snakes.util.{Dice, Event, Observable, UndoManager}

case class Controller(var game: aGame) extends Observable {
  val undoManager = new UndoManager

  def start: aGame = {
    if (!game.gameStarted) {
      game = game.startGame
      notifyObservers(Event.Start)
    }
    game
  }
  def create(size:Int): Unit = {
    game = game.createGame(size)
    notifyObservers(Event.Create)
  }
  def addPlayer(name:String): Unit =
    game = game.createPlayer(name)
    notifyObservers(Event.AddPlayer)

  def roll(): Unit =
    val rollResult = Dice().rollDice
    undoManager.doStep(RollCommand(this, rollResult))
    notifyObservers(Event.Roll(rollResult))

  def undo(): Unit =
    undoManager.undoStep()
    notifyObservers(Event.Undo)

  def exit(): Unit = {
    sys.exit(0)
  }

  override def toString: String =
    game.toString
}