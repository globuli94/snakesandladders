package snakes

import aview.{GUI, TUI}
import controller.Controller
import model.aGame
import snakes.util.Event

import scala.io.StdIn.readLine

object SnakesAndLadders {
  val game: aGame = aGame()
  val controller: Controller = Controller(game)
  val tui = TUI(controller)
  val gui = new GUI(controller)

  controller.notifyObservers(Event.Create)

  def main(args: Array[String]): Unit = {
    println("Welcome to Snakes and Ladders")
    println("here is how to play this game:")
    println("to create a board use <create SIZE>")
    println("to add players use <add NAME>")
    println("You can start the game by rolling the dice using <roll>")
    println("have fun!")
    var input: String = ""

    while(input != "exit") {
      input = readLine()
      tui.getInputAndPrintLoop(input)
    }
  }
}