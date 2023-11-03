package snakes
package util
import scala.util.Random

class Dice {
  def rollDice: Int =
    Random.nextInt(6) + 1
}