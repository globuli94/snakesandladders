package snakes.util

import snakes.controller.ICommand

trait IUndoManager {
  def doStep(command: ICommand): Unit
  def undoStep(): Option[ICommand]
}
class UndoManager extends IUndoManager {
  private var undoStack: List[ICommand] = Nil
  private var redoStack: List[ICommand] = Nil

  override def doStep(command: ICommand): Unit = {
    undoStack = command :: undoStack
    command.doStep()
    redoStack = Nil
  }

  override def undoStep(): Option[ICommand] = {
    undoStack match {
      case Nil => None
      case head :: stack =>
        head.undoStep()
        undoStack = stack
        redoStack = head :: redoStack
        Some(head)
    }
  }
}