package snakes

import com.google.inject.AbstractModule
import net.codingwell.scalaguice.ScalaModule
import snakes.controller.{Controller, ControllerInterface}
import snakes.model.gameComponent.{GameInterface, Game}
import snakes.model.fileIoComponent._

class SnakesModule extends AbstractModule with ScalaModule {
  override def configure(): Unit =
    val game = Game()
    bind[GameInterface].toInstance(game)
    bind[ControllerInterface].to[Controller]
}
