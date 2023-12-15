package snakes
package aview

import util.{Event, Observer}
import snakes.controller.controllerComponent.{Controller, IGameController}
import snakes.model.boardComponent.IBoard
import snakes.model.playerComponent.IPlayer

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

class TUI(controller: IGameController) extends IUserInputHandler with IGameView with Observer {
  controller.add(this)

  override def handleInput(input: String): Unit = {
    val splitInput = input.split(" ")
    splitInput(0) match {
      case "create" => Try(splitInput(1).toInt) match {
        case Success(value) => controller.createGame(value)
        case Failure(_) => println("Invalid command for create.")
      }
      case "add" =>
        controller.addPlayer(splitInput.lift(1).getOrElse(""))
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

  override def updateBoard(board: IBoard): Unit = {
    println("Board Updated:")
  }

  override def displayPlayerInfo(players: List[IPlayer]): Unit = {
    println("Players Information:")
    players.foreach { player =>
      println(s"${player.getName} at position ${player.getPosition}")
    }
  }

  override def showGameStatus(message: String): Unit = {
    println(s"Game Status: $message")
  }

  override def promptForUserInput(): Unit = {
    println("Enter your command:")
    val input = scala.io.StdIn.readLine()
    handleInput(input)
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