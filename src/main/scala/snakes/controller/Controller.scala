package snakes
package controller

import snakes.model.aGame
import snakes.util.{Dice, Observable}

trait Command{
  def execute(): Unit
}
class StartCommand(controller: Controller) extends Command {
  def execute(): Unit = controller.start
}
class RedoCommand(controller: Controller) extends Command {
  override def execute(): Unit = controller.redo
}
class UndoCommand(controller: Controller) extends Command {
  override def execute(): Unit = controller.undo
}
class CreateCommand(controller: Controller, size: Int) extends Command {
  def execute(): Unit = controller.create(size)
}

class AddPlayerCommand(controller: Controller, name: String) extends Command {
  def execute(): Unit = controller.addPlayer(name)
}

class RollCommand(controller: Controller) extends Command {
  def execute(): Unit = {
    controller.roll
    controller.saveState
  }
}
class UnknownCommand extends Command {
  def execute(): Unit = println("Not a valid command!")
}


case class Controller(var game: aGame) extends Observable {

  private val gameHistory = new GameHistory()


  def start: aGame = {
    if (!game.gameStarted) {
      game = game.startGame
      notifyObservers
    }
    game
  }
  def undo: Unit = {
    gameHistory.undo.map {memento =>
      game = game.restoreFromMemento(memento)
      notifyObservers
    }
  }

  def redo: Unit = {
    gameHistory.redo.map { memento =>
      game = game.restoreFromMemento(memento)
      notifyObservers
    }
  }


  def saveState: Unit = {
    gameHistory.saveStateForUndo(game.saveToMemento)
  }

  def create(size: Int): aGame = {
    if (!game.gameStarted) {
      updateGame(game.createGame(size))
    } else {
      println("Game has already started. Cannot create a new board.")
      game
    }
  }

  def addPlayer(name: String): aGame = {
    if (!game.gameStarted) {
      updateGame(game.createPlayer(name))
    } else {
      println("Game has already started. Cannot add new players.")
      game
    }
  }

  def roll: aGame = {
    updateGame(game.moveNextPlayer(Dice().rollDice))
  }

  def updateGame(updatedGame:aGame): aGame =
    game = updatedGame
    notifyObservers
    game

  override def toString: String =
    game.toString
}