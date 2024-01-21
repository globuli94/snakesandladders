package snakes

import aview.{GUI, TUI}
import com.google.inject.Guice
import snakes.controller.{Controller, ControllerInterface}
import snakes.model.gameComponent.Game
import snakes.util.Event

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.io.StdIn.readLine
import java.io.FileInputStream

object SnakesAndLadders {
  val injector = Guice.createInjector(new SnakesModule)
  val controller = injector.getInstance(classOf[ControllerInterface])
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
