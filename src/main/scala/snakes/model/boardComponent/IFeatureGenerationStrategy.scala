package snakes.model.boardComponent

trait IFeatureGenerationStrategy {
  def createFeatures(size: Int): Map[Int, Int]
}