package snakes.model.boardComponent

trait IBoard {
  def getSize: Int
  def getSnakes: Map[Int, Int]
  def getLadders: Map[Int, Int]
}