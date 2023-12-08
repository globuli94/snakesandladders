package snakes.controller

import snakes.model.aGame
import snakes.util.*

class RollCommand(controller: Controller, number: Int) extends Command {
  private val gameState = controller.game

  override def doStep(): Unit =
    controller.game = controller.game.moveNextPlayer(number)

  override def undoStep(): Unit =
    controller.game = gameState
}