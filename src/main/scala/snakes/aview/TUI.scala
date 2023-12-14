package snakes
package aview

import util.{Event, Observer}
import controller.{Controller, IGameController}
import snakes.model.{IBoard, IPlayer}

import scala.util.{Failure, Success, Try}

trait IGameView {
  def updateBoard(board: IBoard): Unit
  def displayPlayerInfo(players: List[IPlayer]): Unit
  def showGameStatus(message: String): Unit
  def promptForUserInput(): Unit
}


trait IUserInputHandler {
  def handleInput(input: String): Unit
}

class TUI(controller:IGameController) extends Observer {
  controller.add(this)

  def getInputAndPrintLoop(input:String): Unit =
    val splitInput = input.split(" ")
    val command = splitInput(0)

    splitInput(0) match
      case "create" => Try(splitInput(1).toInt) match
        case Success(value) => controller.createGame(value)
        case Failure(_) => new IllegalArgumentException("Invalid command")
      case "add" =>
        controller.addPlayer(splitInput(1))
      case "start" =>
        controller.startGame()
      case "roll" => 
        controller.rollDice()
      case "undo" =>
        controller.undoLastAction()
      /*
      case "redo" =>
        controller.redo

       */
      case "exit" =>
        controller.exitGame
      case _
      => println("not a valid command!")

  override def update(e: Event): Unit =
    e match {
      case Event.Roll(rollResult) =>
        println(controller.toString)
      case Event.Undo => println(controller.toString)
      case Event.Create => println(controller.toString)
      case Event.AddPlayer => println(controller.toString)
      case Event.Start => println(controller.toString)
    }
}