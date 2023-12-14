package snakes.model

import scala.util.Random

trait IBoard {
  def getSize: Int
  def getSnakes: Map[Int, Int]
  def getLadders: Map[Int, Int]
}
trait IBoardFactory {
  def createBoard(size: Int, snakeStrategy: IFeatureGenerationStrategy, ladderStrategy: IFeatureGenerationStrategy): IBoard
}
trait IFeatureGenerationStrategy {
  def createFeatures(size: Int): Map[Int, Int]
}

case class Board(size: Int, snakes: Map[Int, Int], ladders: Map[Int, Int]) extends IBoard {
  override def getSize: Int = size
  override def getSnakes: Map[Int, Int] = snakes
  override def getLadders: Map[Int, Int] = ladders
}

class DefaultSnakeCreationStrategy extends IFeatureGenerationStrategy {
  private val random = new Random(0)

  override def createFeatures(size: Int): Map[Int, Int] = {
    val numFeatures = (size * 0.1).toInt
    var features = Map[Int, Int]()

    while (features.size < numFeatures) {
      val start = random.nextInt(size - 1) + 1
      val length = random.nextInt((0.25 * size).toInt) + (0.05 * size).toInt
      val end = start - length

      if (end > 0 && !features.contains(start) && !features.contains(end)) {
        features += (start -> end)
      }
    }
    features
  }
}
class DefaultLadderCreationStrategy extends IFeatureGenerationStrategy {
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

trait BoardFactory {
  def createBoard(size: Int, snakeStrategy: IFeatureGenerationStrategy, ladderStrategy: IFeatureGenerationStrategy): Board
}
class ConfigurableBoardFactory extends BoardFactory {
  override def createBoard(size: Int, snakeStrategy: IFeatureGenerationStrategy, ladderStrategy: IFeatureGenerationStrategy): Board = {
    val snakes = snakeStrategy.createFeatures(size)
    val ladders = ladderStrategy.createFeatures(size)
    Board(size, snakes, ladders)
  }
}


object Board {
  private val defaultSnakeStrategy = new DefaultSnakeCreationStrategy()
  private val defaultLadderStrategy = new DefaultLadderCreationStrategy()
  def createBoard(size: Int): Board = {
    val factory = new ConfigurableBoardFactory()
    factory.createBoard(size, defaultSnakeStrategy, defaultLadderStrategy)
  }
}