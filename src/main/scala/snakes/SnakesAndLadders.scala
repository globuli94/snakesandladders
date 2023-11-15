package snakes

import aview.TUI
import controller.Controller
import model.aGame

import scala.io.StdIn.readLine

object SnakesAndLadders {
  val game: aGame = aGame()
  val controller: Controller = Controller(game)
  val tui = TUI(controller)
  controller.notifyObservers
  
  def main(args: Array[String]): Unit = {
    tui.setupGameMode()
    var input: String = ""

    while(input != "exit") {
      input = readLine()
      tui.getInputAndPrintLoop(input)
    }
  }
}