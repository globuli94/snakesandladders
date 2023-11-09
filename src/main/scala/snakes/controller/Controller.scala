package snakes
package controller

import snakes.model.aGame
import snakes.util.{Dice, Observable}

case class Controller(var game: aGame) extends Observable {
  def addPlayer(name:String): aGame =
    updateGame(game.createPlayer(name))

  def roll: aGame =
    updateGame(game.moveNextPlayer(Dice().rollDice))

  def updateGame(updatedGame:aGame): aGame =
    game = updatedGame
    notifyObservers
    game

  def exit: Nothing =
    sys.exit(0);

  override def toString: String =
    game.toString
}