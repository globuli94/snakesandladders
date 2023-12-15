package snakes.model.boardComponent

trait IBoardFactory {
  def createBoard(size: Int, snakeStrategy: IFeatureGenerationStrategy, ladderStrategy: IFeatureGenerationStrategy): IBoard
}