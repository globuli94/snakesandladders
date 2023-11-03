package snakes
package aview

import util.Observer
import controller.Controller
import scala.io.StdIn.readLine

class TUI(controller:Controller) extends Observer {
  controller.add(this)
  def run(): Unit =
    println("Snakes and Ladders:\n")
    getInputAndPrintLoop()

  def getInputAndPrintLoop(): Unit =
    println("Enter your next command: ")
    val input = readLine
    val splitInput = input.split(" ")
    val command = splitInput(0)
    
    splitInput(0) match
      case "exit" =>
        println("exiting...!")
        controller.exit
      case "add" =>
        controller.addPlayer(splitInput(1))
        println(controller.game.queue.last.name + "has been added!")
      case "start"
        => controller.loop()
      case "roll"
        => controller.roll
      case _
        => println("not a valid command!")


  override def update: Unit =
    println(controller.toString)
    getInputAndPrintLoop()
}