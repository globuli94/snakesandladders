package snakes
package aview

import util.Observer
import controller.Controller

import scala.util.{Failure, Success, Try}

class TUI(controller:Controller) extends Observer {
  controller.add(this)

  def getInputAndPrintLoop(input:String): Unit =
    val splitInput = input.split(" ")
    val command = splitInput(0)

    splitInput(0) match
      case "create" => Try(splitInput(1).toInt) match
        case Success(value) => controller.create(value)
        case Failure(_) => new IllegalArgumentException("Invalid command")
      case "add" =>
        controller.addPlayer(splitInput(1))
      case "roll" => 
        controller.roll
      case "undo" =>
        controller.undo
      /*
      case "redo" =>
        controller.redo

       */
      case _
      => println("not a valid command!")

  override def update: Unit =
    println(controller.toString)
}