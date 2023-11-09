package snakes
package aview

import util.Observer
import controller.Controller
import scala.io.StdIn.readLine

class TUI(controller:Controller) extends Observer {
  controller.add(this)

  def getInputAndPrintLoop(input:String): Unit =
    val splitInput = input.split(" ")
    val command = splitInput(0)

    splitInput(0) match
      case "add" =>
        controller.addPlayer(splitInput(1))
      case "roll"
      => controller.roll
      case _
      => println("not a valid command!")
  
  override def update: Unit =
    println(controller.toString)
}