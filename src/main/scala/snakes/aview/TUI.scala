package snakes
package aview

import util.Observer
import controller.Controller
import scala.io.StdIn.readLine

class TUI(controller:Controller) extends Observer {
  controller.add(this)
  def run(): Unit =
    println("Snakes and Ladders:\n")

  def getInputAndPrintLoop(): Unit =
    println("Enter your next command: ")
    val input = readLine
    input match
      case "exit"
        => controller.exit
      case "add"
        => controller.addPlayer(input.split(" ")(1))
      case "start"
        => controller.loop()
}