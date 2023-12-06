package snakes
package controller

import snakes.model.aGame
import snakes.util.{Dice, Event, Observable, UndoManager}

case class Controller(var game: aGame) extends Observable {
  val undoManager = new UndoManager

  def create(size:Int): Unit = {
    game = game.createGame(size)
    notifyObservers(Event.Create)
  }
  def addPlayer(name:String): Unit = 
    game = game.createPlayer(name)
    notifyObservers(Event.AddPlayer)
  
  def roll: Unit =
    undoManager.doStep(RollCommand(this, Dice().rollDice))
    notifyObservers(Event.Roll)

  def undo: Unit =
    undoManager.undoStep()
    notifyObservers(Event.Undo)
  /*
  def redo: Unit =
    undoManager.redoStep()
    notifyObservers

   */

  override def toString: String =
    game.toString
}