package snakes.controller

import snakes.SnakesAndLadders.game
import snakes.model.GameMemento


class GameHistory {
  private var undoHistory: List[GameMemento] = List()
  private var redoHistory: List[GameMemento] = List()

  def saveStateForUndo(memento: GameMemento): Unit = {
    undoHistory = memento :: undoHistory
  }

  def saveStateForRedo(memento: GameMemento): Unit = {
    redoHistory = memento :: redoHistory
  }

  def undo: Option[GameMemento] = {
    if (undoHistory.nonEmpty) {
      val currentState = undoHistory.head
      undoHistory = undoHistory.tail
      saveStateForRedo(currentState)
      undoHistory.headOption
    } else {
      None
    }
  }

  def redo: Option[GameMemento] = {
    redoHistory match {
      case memento :: tail =>
        redoHistory = tail
        saveStateForUndo(memento)
        Some(memento)
      case Nil => None
    }
  }
}
