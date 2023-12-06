package snakes.aview

import snakes.controller.Controller
import snakes.util.{Event, Observer}

import scala.swing.*
import scala.swing.event.*


class GUI(controller: Controller) extends Frame with Observer {
  controller.add(this)
  // Menu Bar with AddPlayer and Exit
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

  contents = updateContents
  pack()
  centerOnScreen()
  open()

  // Panel for creating the Game Size
  class SizeOptionPanel(controller: Controller) extends BoxPanel(Orientation.Horizontal) {
      contents += new Button(Action("8x8") {
        controller.create(8)
      })
      contents += new Button(Action("10x10") {
        controller.create(10)
      })
      contents += new Button(Action("12x12") {
        controller.create(12)
      })
  }

  class FieldGridPanel(controller: Controller) extends GridPanel(controller.game.board.size, controller.game.board.size) {

  }

  class FieldPanel() {

  }

  def updateContents = {
    new BorderPanel {
      add(new SizeOptionPanel(controller), BorderPanel.Position.North)
    }
  }

  def linearIndexToCustomOrder(size: Int, linearIndex: Int): Int = {
    val totalIndices = size * size
    val reversedIndex = totalIndices - 1 - linearIndex
    val row = reversedIndex / size
    val col = reversedIndex % size
    val customOrderIndex = col * size + row
    customOrderIndex
  }

  def update(e: Event): Unit = {
    e match
      case Event.Create =>
      case Event.AddPlayer =>
      case Event.Undo =>
      case Event.Roll =>
      case Event.Exit =>
  }
}


