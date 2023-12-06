package snakes
package aview

import util.Observer
import controller.{AddPlayerCommand, Command, Controller, CreateCommand, RedoCommand, RollCommand, StartCommand, UndoCommand, UnknownCommand}

import scala.util.{Failure, Success, Try}


class TUI(controller:Controller) extends Observer {
  controller.add(this)

  def getInputAndPrintLoop(input: String): Unit = {
    val splitInput = input.split(" ")
    val command: Command = splitInput(0) match {
      case "create" => Try(splitInput(1).toInt) match {
        case Success(size) => new CreateCommand(controller, size)
        case Failure(_) => new UnknownCommand()
      }
      case "add"    => new AddPlayerCommand(controller, splitInput(1))
      case "start" => new StartCommand(controller)
      case "roll"   => new RollCommand(controller)
      case "undo"   => new UndoCommand(controller)
      case "redo"   => new RedoCommand(controller)
      case _        => new UnknownCommand()
    }
    command.execute()
  }

  override def update: Unit =
    println(controller.toString)
}