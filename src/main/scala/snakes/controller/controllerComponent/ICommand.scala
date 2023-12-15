package snakes.controller.controllerComponent

trait ICommand {
  def doStep(): Unit
  def undoStep(): Unit
}