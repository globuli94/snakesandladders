package snakes.util

trait CommandInterface {
  def doStep(): Unit
  def undoStep(): Unit
  //def redoStep(): Unit
}
