package snakes.model.boardComponent

trait BoardFactoryInterface {
  def createBoard(size: Int, snakeStrategy: FeatureGenerationStrategyInterface, ladderStrategy: FeatureGenerationStrategyInterface): BoardInterface
}