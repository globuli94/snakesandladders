package snakes
package aview

import util.Observer
import controller.{AddPlayerCommand, Command, Controller, CreateCommand, RollCommand, UnknownCommand}

class TUI(controller:Controller) extends Observer {
  controller.add(this)

  def getInputAndPrintLoop(input: String): Unit = {
    val splitInput = input.split(" ")
    val command: Command = splitInput(0) match {
      case "create" => new CreateCommand(controller, splitInput(1).toInt)
      case "add"    => new AddPlayerCommand(controller, splitInput(1))
      case "roll"   => new RollCommand(controller)
      case _        => new UnknownCommand()
    }
    command.execute()
  }

  override def update: Unit =
    println(controller.toString)
}