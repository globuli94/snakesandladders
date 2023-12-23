package snakes.model.boardComponent

trait BoardInterface {
  def getSize: Int
  def getSnakes: Map[Int, Int]
  def getLadders: Map[Int, Int]
}