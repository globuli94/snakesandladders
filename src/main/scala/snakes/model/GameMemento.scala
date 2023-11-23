package snakes.model

import scala.collection.immutable.Queue

case class GameMemento(board: Board, queue: Queue[Player])

