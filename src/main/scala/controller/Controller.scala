package controller

import model.Game
import model.Player
import util.Observable

case class Controller(var game: Game) extends Observable {
  def movePlayer(player:Player): Unit = {

  }
}
