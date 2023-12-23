package snakes.model.playerComponent

import java.awt.Color
import scala.swing.Color

case class Player(name: String, position: Int, color: Color, lastRoll: Int = 0) extends PlayerInterface {
  override def getName: String = name
  override def getPosition: Int = position
  override def getColor: Color = color
  override def getLastRoll: Int = lastRoll

  override def moveTo(newPosition: Int, newLastRoll: Int): PlayerInterface = {
    copy(position = newPosition, lastRoll = newLastRoll)
  }
}

object Player {
  class Builder(private var name: String = "", private var position: Int = 1, private var color: Color = new Color(128, 128, 128), private var lastRoll: Int = 0) {
    def setName(newName: String): Builder = {
      name = newName
      this
    }
    def setPosition(newPosition: Int): Builder = {
      position = newPosition
      this
    }

    def setColor(x:Int): Builder = {
      val colorMap: Map[Int, Color] = Map(
        0 -> Color.green,
        1 -> Color.yellow,
        2 -> Color.black,
        3 -> Color.blue,
        4 -> Color.orange
      )

      color = colorMap(x)
      this
    }

    def build(): Player = {
      new Player(name, position, color, lastRoll)
    }
  }
  def builder(): Builder = new Builder()
}