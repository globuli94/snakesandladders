package snakes.controller

import snakes.model.gameComponent.{GameInterface, Game}
import snakes.util.*

class RollCommand(controller: ControllerInterface, private val rollResult: Int) extends CommandInterface {
  private var previousState: Option[GameInterface] = None

  override def doStep(): Unit = {
    previousState = Some(controller.getCurrentGameState)

    controller.setGameState(controller.getCurrentGameState.moveNextPlayer(rollResult))
  }

  override def undoStep(): Unit = {
    previousState match {
      case Some(state) =>
        controller.setGameState(state)
      case None =>
    }
  }
}