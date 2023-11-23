package snakes.model
case class Player private (name: String, position: Int) {
  def moveTo(x: Int): Player = {
    Player(name, x)
  }
}
object Player {
  class Builder(private var name: String = "", private var position: Int = 0) {
    def setName(newName: String): Builder = {
      name = newName
      this
    }
    def setPosition(newPosition: Int): Builder = {
      position = newPosition
      this
    }
    def build(): Player = {
      new Player(name, position)
    }
  }
  def builder(): Builder = new Builder()
}