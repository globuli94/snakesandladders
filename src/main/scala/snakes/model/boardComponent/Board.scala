package snakes.model.boardComponent

import snakes.model.*

import scala.util.Random

case class Board(size: Int, snakes: Map[Int, Int], ladders: Map[Int, Int]) extends BoardInterface {
  override def getSize: Int = size
  override def getSnakes: Map[Int, Int] = snakes
  override def getLadders: Map[Int, Int] = ladders
}

object Board {
  private val defaultSnakeStrategy = new DefaultSnakeCreationStrategy()
  private val defaultLadderStrategy = new DefaultLadderCreationStrategy()
  def createBoard(size: Int): Board = {
    val factory = new ConfigurableBoardFactory()
    factory.createBoard(size, defaultSnakeStrategy, defaultLadderStrategy)
  }
}