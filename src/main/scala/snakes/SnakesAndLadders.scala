package snakes

import aview.TUI
import controller.Controller
import model.aGame
@main def main: Unit =
  println("Welcome to TicTacToe")
  val game = aGame()
  val controller = Controller(game)
  val tui = TUI(controller)
  tui.run()
