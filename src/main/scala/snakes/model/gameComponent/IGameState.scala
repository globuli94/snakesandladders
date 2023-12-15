package snakes.model.gameComponent

import snakes.model.boardComponent.IBoard
import snakes.model.playerComponent.IPlayer

trait IGameState {
  def getBoard: IBoard
  def getPlayers: List[IPlayer]
  def getCurrentPlayer(): IPlayer
  def isGameStarted(): Boolean
  def startGame: IGameState
  def moveNextPlayer( roll: Int): IGameState
  def createGame(size: Int): IGameState
  def createPlayer(name: String): IGameState
}