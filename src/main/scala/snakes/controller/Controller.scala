package snakes
package controller

import model.aGame
import model.Player
import util.Observer

case class Controller(var game: aGame) extends Observable {
  def loop(): aGame =
    while(game.queue.last.position != game.board.size) {
      updateGame(game)
    }
    game

  def addPlayer(name:String): aGame =
    updateGame(game.createPlayer(name))

  def roll: aGame =
    updateGame(game.moveNextPlayer)

  def updateGame(updatedGame:aGame): aGame =
    game = updatedGame
    notifyObservers
    game

  def exit: Nothing =
    sys.exit(0);

  override def toString: String =
    val stringBuilder = new StringBuilder("Player: ")
    game.queue.foreach(element =>
      stringBuilder.append(element.name + "(")
      stringBuilder.append(element.position + ") ")
    )
    stringBuilder.append("\nNext Player up is: " + game.queue.head.name + "\n")
    stringBuilder.toString()
}
