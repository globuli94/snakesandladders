package snakes

import aview.{GUI, TUI}
import com.google.inject.Guice
import snakes.controller.controllerComponent.{Controller, IGameController}
import snakes.model.gameComponent.aGame
import snakes.util.Event

import scala.io.StdIn.readLine

object SnakesAndLadders {

  val controller = Guice.createInjector(new SnakesModule).getInstance(classOf[IGameController])
  val tui = TUI(controller)
  val gui = new GUI(controller)

  controller.notifyObservers(Event.Create)

  def main(args: Array[String]): Unit = {
    var input: String = ""

    while(input != "exit") {
      input = readLine()
      tui.handleInput(input)
    }
  }
}