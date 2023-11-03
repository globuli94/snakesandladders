package snakes
package model
case class Player(name:String, position:Int) {
  def move(x:Int): Player = {
    Player(name, position + x)
  }
}