package snakes.controller

import snakes.model.GameMemento


class GameHistory {
  private var history: List[GameMemento] = List()

  def saveState(memento: GameMemento): Unit =
    history = memento :: history

  def restoreState: Option[GameMemento] =
    if (history.nonEmpty) {
      val lastState = history.head
      history = history.tail
      Some(lastState)
    } else None
}