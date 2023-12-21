package snakes

import com.google.inject.AbstractModule
import net.codingwell.scalaguice.ScalaModule
import snakes.controller.controllerComponent.{Controller, IGameController}
import snakes.model.gameComponent.{IGameState, aGame}

class SnakesModule extends AbstractModule with ScalaModule {
  override def configure(): Unit =
    val game = aGame()
    bind[IGameState].toInstance(game)
    bind(classOf[IGameController]).to(classOf[Controller])
}
