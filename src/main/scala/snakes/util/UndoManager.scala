package snakes.util

trait IUndoManager {
  def doStep(command: CommandInterface): Unit
  def undoStep(): Option[CommandInterface]
}
class UndoManager extends IUndoManager {
  private var undoStack: List[CommandInterface] = Nil
  private var redoStack: List[CommandInterface] = Nil

  override def doStep(command: CommandInterface): Unit = {
    undoStack = command :: undoStack
    command.doStep()
    redoStack = Nil
  }

  override def undoStep(): Option[CommandInterface] = {
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