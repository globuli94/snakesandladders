package snakes.model.gameComponent

import snakes.model.boardComponent.IBoard
import snakes.model.playerComponent.IPlayer

import scala.collection.immutable.Queue

trait IGameState {
  def getBoard: IBoard
  def getPlayers: Queue[IPlayer]
  def getCurrentPlayer(): IPlayer
  def isGameStarted(): Boolean
  def startGame: IGameState
  def moveNextPlayer( roll: Int): IGameState
  def createGame(size: Int): IGameState
  def createPlayer(name: String): IGameState
}