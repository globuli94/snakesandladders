package snakes.controller

import snakes.model.gameComponent.GameInterface
import snakes.util.{CommandInterface, Observable}

trait ControllerInterface extends Observable {
  def startGame(): Unit
  def createGame(size: Int): Unit
  def addPlayer(name: String): Unit
  def rollDice(): Unit
  def undoLastAction(): Unit
  def getCurrentGameState: GameInterface
  def exitGame(): Unit
  def setGameState(state: GameInterface): Unit
  def saveGame(): Unit
  def loadGame(): Unit
  def executeCommand(command: CommandInterface):Unit
  def getBoardSize:Int
  def checkWin(): Boolean
  def restartGame(): Unit
}