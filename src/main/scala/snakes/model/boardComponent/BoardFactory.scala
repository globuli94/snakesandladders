package snakes.model.boardComponent

trait BoardFactory {
  def createBoard(size: Int, snakeStrategy: IFeatureGenerationStrategy, ladderStrategy: IFeatureGenerationStrategy): IBoard
}
class ConfigurableBoardFactory extends BoardFactory {
  override def createBoard(size: Int, snakeStrategy: IFeatureGenerationStrategy, ladderStrategy: IFeatureGenerationStrategy): Board = {
    val snakes = snakeStrategy.createFeatures(size)
    val ladders = ladderStrategy.createFeatures(size)
    Board(size, snakes, ladders)
  }
}