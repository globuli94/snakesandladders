package snakes.model

import scala.util.Random

case class Board(size:Int, snakes: Map[Int, Int], ladders: Map[Int, Int])

object Board {
  def apply(size: Int): Board = {
    val (snakes, ladders) = generateSnakesAndLadders(size)
    new Board(size, snakes, ladders)
  }

  private def generateSnakesAndLadders(size: Int): (Map[Int, Int], Map[Int, Int]) = {
    val numFeatures = (size * 0.1).toInt
    val lengths = (0.05 * size).toInt to (0.15 * size).toInt

    def generateFeature(isSnake: Boolean): Map[Int, Int] = {
      var features = Map[Int, Int]()
      while (features.size < numFeatures) {
        val start = Random.nextInt(size - 3) + 1 // avoid last 3 cells for snakes
        val end = if (isSnake) start - Random.shuffle(lengths).head else start + Random.shuffle(lengths).head
        if (end > 0 && end <= size && !features.contains(start) && !features.contains(end)) {
          features += (start -> end)
        }
      }
      features
    }

    (generateFeature(isSnake = true), generateFeature(isSnake = false))
  }
}