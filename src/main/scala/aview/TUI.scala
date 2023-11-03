package aview

class TUI {
    def displayMessage(message: String): Unit = {
      println(message)
    }

    def displayBoard(playerPosition: Int): Unit = {
      println(s"Player is at position $playerPosition")

}
