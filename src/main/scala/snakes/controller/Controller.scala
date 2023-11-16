package snakes
package controller

import snakes.model.aGame
import snakes.util.{Dice, Observable}

case class Controller(var game: aGame) extends Observable {
  def addPlayer(name:String): aGame =
    updateGame(game.createPlayer(name))

  def roll: aGame = {

    val oldPosition = game.queue.head.position
    val updatedGame = updateGame(game.moveNextPlayer(Dice().rollDice))
    val newPosition = updatedGame.queue.last.position

    if (updatedGame.board.snakes.contains(newPosition)) {
      println(s"Oh no! ${updatedGame.queue.last.name} landed on a snake at $oldPosition and slid down to $newPosition!")
    } else if (updatedGame.board.ladders.contains(newPosition)) {
      println(s"Great! ${updatedGame.queue.last.name} found a ladder at $oldPosition and climbed up to $newPosition!")
    }

    updateGame(game.moveNextPlayer(Dice().rollDice))
  }

  def updateGame(updatedGame:aGame): aGame ={
    game = updatedGame
    notifyObservers
    game
  }

  def setupGame(length: String, difficulty: String, playerNames: List[String]): Unit = {
    updateGame(game.setupGame(length, difficulty, playerNames))
  }


  override def toString: String =
    game.toString
}