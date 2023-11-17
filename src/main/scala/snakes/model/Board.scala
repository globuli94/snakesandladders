package snakes.model

import scala.util.Random

case class Board(size: Int, snakes: Map[Int, Int], ladders: Map[Int, Int])

object Board {
  private val random = new Random(0)

  def apply(size: Int): Board = {
    val (snakes, ladders) = generateSnakesAndLadders(size)
    new Board(size, snakes, ladders)
  }

  private def generateSnakesAndLadders(size: Int): (Map[Int, Int], Map[Int, Int]) = {
    val numFeatures = (size * 0.1).toInt

    def generateFeature(isSnake: Boolean, existingFeatures: Map[Int, Int]): Map[Int, Int] = {
      var features = Map[Int, Int]()
      while (features.size < numFeatures) {
        val start = random.nextInt(size - 1) + 1
        val length = random.nextInt((0.25 * size).toInt) + (0.05 * size).toInt
        val end = if (isSnake) start - length else start + length

        if (end > 0 && end <= size && !features.contains(start) && !existingFeatures.contains(end) && !existingFeatures.contains(start)) {
          features += (start -> end)
        }
      }
      features
    }

    val snakes = generateFeature(isSnake = true, Map.empty)
    val ladders = generateFeature(isSnake = false, snakes)

    (snakes, ladders)
  }
}
