package snakes.model.playerComponent

import scala.swing.Color

trait PlayerInterface {
  def getName: String
  def getPosition: Int
  def getColor: Color
  def getLastRoll: Int
  def moveTo(position: Int, lastRoll: Int): PlayerInterface
}

