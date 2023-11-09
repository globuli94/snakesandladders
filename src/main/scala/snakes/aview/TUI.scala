package snakes.aview

import snakes.util.Observer

class TUI extends Observer {
  override def update(playerNumber: Int, position: Int, roll: Int): Unit = {
    if (roll == -1) {
      println(s"Player $playerNumber wins!")
    } else {
      println(s"Player $playerNumber rolled a $roll and is now on cell $position")
    }
  }
}
