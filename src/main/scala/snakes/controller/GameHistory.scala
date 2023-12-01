package snakes.controller

import snakes.SnakesAndLadders.game
import snakes.model.GameMemento


class GameHistory {
  private var undoHistory: List[GameMemento] = List()
  private var redoHistory: List[GameMemento] = List()
  private var lastOperationWasUndo: Boolean = false

  def saveState(memento: GameMemento): Unit =
    undoHistory = memento :: undoHistory
    redoHistory = List()
    lastOperationWasUndo = false

  def undo: Option[GameMemento] =
    undoHistory match {
      case head :: next :: tail =>
        undoHistory = next :: tail
        redoHistory = head :: redoHistory
        lastOperationWasUndo = true
        Some(next)
      case Nil => None
    }

  def redo: Option[GameMemento] = {
    if (lastOperationWasUndo && redoHistory.nonEmpty) {
      redoHistory match {
        case head :: tail =>
          redoHistory = tail
          undoHistory = head :: undoHistory
          lastOperationWasUndo = false
          Some(head)
      }
    } else {
      None
    }
  }
}