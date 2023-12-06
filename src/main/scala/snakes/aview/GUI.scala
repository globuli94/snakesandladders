package snakes.aview

import snakes.controller.Controller
import snakes.util.{Event, Observer}

import scala.swing.*
import scala.swing.event.*


class GUI(controller: Controller) extends Frame with Observer {
  controller.add(this)

  title = "Snakes and Ladders"
  menuBar = new MenuBar {
    contents += new Menu("Game") {
      contents += new MenuItem(Action("Add Player") {
        val playerName = Dialog.showInput[String](null,
          null, "Enter Player Name", Dialog.Message.Plain, Swing.EmptyIcon, Nil,"")
        playerName match {
          case Some(name) =>
            if(name.equals("")) {
              Dialog.showMessage(null, "No Player Added!", "Error", Dialog.Message.Plain, Swing.EmptyIcon)
            } else {
              controller.addPlayer(name)
            }
          case None =>
        }
      })
      contents += new MenuItem(Action("Exit") {
        controller.exit()
      })
    }
  }
  pack()
  centerOnScreen()
  open()

  

  def update(e: Event): Unit = {
    e match
      case Event.Create =>
      case Event.AddPlayer =>
      case Event.Undo =>
      case Event.Roll =>
      case Event.Exit =>
  }
}


