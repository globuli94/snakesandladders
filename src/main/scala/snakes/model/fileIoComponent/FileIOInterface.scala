package snakes.model.fileIoComponent

import snakes.model.gameComponent.GameInterface

trait FileIOInterface {
  def load: GameInterface
  def save(game: GameInterface): Unit
}
