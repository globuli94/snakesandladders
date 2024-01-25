package snakes
package util

trait Observer {
  def update(e: Event): Unit
}
  

trait Observable {
  var subscribers: Vector[Observer] = Vector()
  def add(s: Observer) = subscribers = subscribers :+ s
  //def remove(s: Observer) = subscribers = subscribers.filterNot(o => o == s)
  def notifyObservers(e: Event) = subscribers.foreach(o => o.update(e))
}

enum Event {
  case Create
  case AddPlayer
  case Roll(rollResult: Int)
  case Undo
  case Start
  case Load
  case Save
  case Update
  case Restart
}