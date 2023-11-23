package snakes
package controller

import snakes.model.aGame
import snakes.util.{Dice, Observable}

trait Command{
  def execute(): Unit
}
class CreateCommand(controller: Controller, size: Int) extends Command {
  def execute(): Unit = controller.create(size)
}

class AddPlayerCommand(controller: Controller, name: String) extends Command {
  def execute(): Unit = controller.addPlayer(name)
}

class RollCommand(controller: Controller) extends Command {
  def execute(): Unit = controller.roll
}
class UnknownCommand extends Command {
  def execute(): Unit = println("Not a valid command!")
}


case class Controller(var game: aGame) extends Observable {

  def create(size:Int): aGame = {
    updateGame(game.createGame(size))
  }
  def addPlayer(name:String): aGame =
    updateGame(game.createPlayer(name))

  def roll: aGame =
    updateGame(game.moveNextPlayer(Dice().rollDice))

  def updateGame(updatedGame:aGame): aGame =
    game = updatedGame
    notifyObservers
    game

  override def toString: String =
    game.toString
}