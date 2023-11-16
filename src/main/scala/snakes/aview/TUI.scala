package snakes
package aview

import util.Observer
import controller.Controller
import scala.io.StdIn.readLine

class TUI(controller:Controller) extends Observer {
  controller.add(this)

  def setupGameMode(): Unit = {
    println("Welcome to Snakes and Ladders!")
    println("Choose game length (short, medium, long):")
    val length = readLine()

    println("Choose difficulty (easy, normal, difficult):")
    val difficulty = readLine()

    println("Enter the number of players:")
    val numPlayers = readLine().toInt

    val playerNames = (1 to numPlayers).map { i =>
      println(s"Enter the name of player $i:")
      readLine()
    }.toList

    controller.setupGame(length, difficulty, playerNames)
  }

  def getInputAndPrintLoop(input:String): Unit = {
    val splitInput = input.split(" ")
    val command = splitInput(0)

    splitInput(0) match
      case "add" =>
        controller.addPlayer(splitInput(1))
      case "roll"
      => controller.roll
      case _
      => println("not a valid command!")
  }
  
  override def update: Unit =
    println(controller.toString)
}
