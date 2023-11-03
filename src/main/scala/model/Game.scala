package model
import scala.collection.immutable.Queue
case class Game(field:Game, queue: Queue[Player] = Queue.empty)