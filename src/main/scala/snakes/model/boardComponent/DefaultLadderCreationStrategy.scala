package snakes.model.boardComponent

import scala.util.Random

class DefaultLadderCreationStrategy extends FeatureGenerationStrategyInterface {
  private val random = new Random(0)

  override def createFeatures(size: Int): Map[Int, Int] = {
    val numFeatures = (size * 0.1).toInt
    var features = Map[Int, Int]()

    while (features.size < numFeatures) {
      val start = random.nextInt(size - 1) + 1
      val length = random.nextInt((0.25 * size).toInt) + (0.05 * size).toInt
      val end = start + length

      if (end <= size && !features.contains(start) && !features.contains(end)) {
        features += (start -> end)
      }
    }
    features
  }
}