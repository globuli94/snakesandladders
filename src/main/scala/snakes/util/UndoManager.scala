package snakes.util

class UndoManager {
  private var undoStack: List[Command]= Nil
  private var redoStack: List[Command]= Nil

  def doStep(command: Command): Unit = {
    undoStack = command::undoStack
    command.doStep()
    redoStack = Nil
  }

  def undoStep(): Option[Command] = {
    undoStack match {
      case Nil => None
      case head::stack => {
        head.undoStep()
        undoStack = stack
        redoStack = head :: redoStack
        Some(head)
      }
    }
  }

  def redoStep(): Option[Command] = {
    redoStack match {
      case Nil => None
      case head :: stack => {
        head.redoStep()
        redoStack = stack
        undoStack = head :: undoStack
        Some(head)
      }
    }
  }
}