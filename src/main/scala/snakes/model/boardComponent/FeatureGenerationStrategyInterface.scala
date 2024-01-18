package snakes.model.boardComponent

trait FeatureGenerationStrategyInterface {
  def createFeatures(size: Int): Map[Int, Int]
}