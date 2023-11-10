package snakes.model

import snakes.util.Observable

class Game(val players: Array[Player], val board: Board, val dice: Dice) extends Observable {
  private var states: Array[Player] = players

  def movePlayer(playerNumber: Int, roll: Int): Int = {
    val player = states(playerNumber - 1)
    var newPosition = player.position + roll

    newPosition = board.snakes.getOrElse(newPosition, newPosition)
    newPosition = board.ladders.getOrElse(newPosition, newPosition)

    states = states.updated(playerNumber - 1, player.copy(position = newPosition))
    notifyObservers(playerNumber, newPosition, roll)
    newPosition
  }

  def checkWin(playerNumber: Int): Boolean = {
    val won = states(playerNumber - 1).position >= 100
    if (won) notifyObservers(playerNumber, -1, -1)
    won
  }
}
