package snakes.controller

import snakes.model.{IGameState, aGame}
import snakes.util.*

class RollCommand(controller: IGameController, private val rollResult: Int) extends ICommand {
  private var previousState: Option[IGameState] = None

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