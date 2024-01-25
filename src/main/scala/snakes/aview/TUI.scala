package snakes
package aview

import snakes.controller.ControllerInterface
import util.{Event, Observer}

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
      case "save" =>
        controller.saveGame()
      case "load" =>
        controller.loadGame()
      case "restart" =>
        controller.restartGame()
      case _ =>
        println("Not a valid command!")
    }
  }

  override def update(e: Event): Unit = {
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
      case Event.Save =>
        println("Game saved.")
      case Event.Load =>
        println("Game loaded.")
      case Event.Restart =>
        println("Returned to Main Menu.")
    }
  }
}