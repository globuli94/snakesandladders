package snakes
package util
import scala.util.Random

trait IDice {
  def rollDice: Int
}

class Dice extends IDice {
  override def rollDice: Int = Random.nextInt(6) + 1
}