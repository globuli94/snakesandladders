package snakes.model.gameComponent

import snakes.model.boardComponent.BoardInterface
import snakes.model.playerComponent.{PlayerInterface}

import scala.collection.immutable.Queue

trait GameInterface {
  def getBoard: BoardInterface
  def getPlayers: Queue[PlayerInterface]
  def getCurrentPlayer(): PlayerInterface
  def isGameStarted(): Boolean
  def startGame: GameInterface
  def moveNextPlayer( roll: Int): GameInterface
  def createGame(size: Int): GameInterface
  def createPlayer(name: String): GameInterface
}