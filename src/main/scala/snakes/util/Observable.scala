package snakes.util


trait Observer {
  def update(playerNumber: Int, position: Int, roll: Int): Unit
}

class Observable {
  protected var observers: List[Observer] = List()

  def addObserver(observer: Observer): Unit = {
    observers = observer :: observers
  }

  def notifyObservers(playerNumber: Int, position: Int, roll: Int): Unit = {
    observers.foreach(_.update(playerNumber, position, roll))
  }
}