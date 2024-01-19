package snakes.model.boardComponent

import scala.util.Random

class DefaultSnakeCreationStrategy extends FeatureGenerationStrategyInterface {
  private val random = new Random(7)

  override def createFeatures(size: Int): Map[Int, Int] = {
    val numFeatures = (size * 0.07).toInt
    var features = Map[Int, Int]()

    while (features.size < numFeatures) {
      val start = random.nextInt(size - 1) + 1
      val length = random.nextInt((0.55 * size).toInt) + (0.13 * size).toInt
      val end = start - length

      if (end > 3 && end < size-3 && !features.contains(start) && !features.contains(end)) {
        features += (start -> end)
      }
    }
    features
  }
}