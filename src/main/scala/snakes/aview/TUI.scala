package snakes
package aview

import util.Observer
import controller.Controller
import scala.io.StdIn.readLine

class TUI(controller:Controller) extends Observer {
  controller.add(this)
  def run(): Unit =
    getInputAndPrintLoop()

  def getInputAndPrintLoop(): Unit =
    println("Enter your next command: ")
    val input = readLine
    val splitInput = input.split(" ")
    val command = splitInput(0)

    splitInput(0) match
      case "exit" =>
        controller.exit
      case "add" =>
        controller.addPlayer(splitInput(1))
      case "roll"
        => controller.roll
      case _
        => println("not a valid command!")

    getInputAndPrintLoop()

  override def update: Unit =
    println(controller.toString)
}