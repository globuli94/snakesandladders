package snakes.model.boardComponent

trait BoardFactory {
  def createBoard(size: Int, snakeStrategy: FeatureGenerationStrategyInterface, ladderStrategy: FeatureGenerationStrategyInterface): BoardInterface
}
class ConfigurableBoardFactory extends BoardFactory {
  override def createBoard(size: Int, snakeStrategy: FeatureGenerationStrategyInterface, ladderStrategy: FeatureGenerationStrategyInterface): Board = {
    val snakes = snakeStrategy.createFeatures(size)
    val ladders = ladderStrategy.createFeatures(size)
    Board(size, snakes, ladders)
  }
}