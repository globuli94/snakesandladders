package snakes
package controller

import snakes.model.aGame
import snakes.util.{Dice, Observable}

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