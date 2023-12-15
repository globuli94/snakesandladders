package snakes.controller.controllerComponent

import snakes.model.gameComponent.IGameState
import snakes.util.Observable

trait IGameController extends Observable {
  def startGame(): Unit
  def createGame(size: Int): Unit
  def addPlayer(name: String): Unit
  def rollDice(): Unit
  def undoLastAction(): Unit
  def getCurrentGameState: IGameState
  def exitGame(): Unit
  def setGameState(state: IGameState): Unit
}