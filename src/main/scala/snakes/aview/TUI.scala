package snakes
package aview

import util.{Event, Observer}
import snakes.controller.controllerComponent.{Controller, ControllerInterface}

import scala.util.{Failure, Success, Try}


trait IUserInputHandler {
  def handleInput(input: String): Unit
}

class TUI(controller: ControllerInterface) extends IUserInputHandler with Observer {
  controller.add(this)

  override def handleInput(input: String): Unit = {
    val splitInput = input.split(" ")
    splitInput(0) match {
      case "create" => Try(splitInput(1).toInt) match {
        case Success(value) => controller.createGame(value)
        case Failure(_) => println("Invalid command for create.")
      }
      case "add" =>
        controller.addPlayer(splitInput(1))
      case "start" =>
        controller.startGame()
      case "roll" =>
        controller.rollDice()
      case "undo" =>
        controller.undoLastAction()
      case "exit" =>
        controller.exitGame()
      case _ =>
        println("Not a valid command!")
    }
  }

  override def update(e: Event): Unit = {
    // Update the TUI based on the event
    e match {
      case Event.Roll(rollResult) =>
        println(s"Player rolled a $rollResult")
        println(controller.getCurrentGameState.toString)
      case Event.Undo =>
        println("Last action was undone.")
        println(controller.getCurrentGameState.toString)
      case Event.Create =>
        println("New game created.")
        println(controller.getCurrentGameState.toString)
      case Event.AddPlayer =>
        println("New player added.")
        println(controller.getCurrentGameState.toString)
      case Event.Start =>
        println("Game started.")
        println(controller.getCurrentGameState.toString)
    }
  }
}