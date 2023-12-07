package snakes.aview

import org.scalactic.source.Position
import snakes.controller.Controller
import snakes.util.{Event, Observer}

import scala.math.sqrt
import java.awt.Color
import javax.swing.{BorderFactory, BoxLayout, ImageIcon}
import scala.swing.*
import scala.swing.Swing.EmptyBorder
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
              val addString = name + " has been added to the game!"
              Dialog.showMessage(null, addString,"Player Added", Dialog.Message.Plain, Swing.EmptyIcon)
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

  contents = updateContents()
  pack()
  centerOnScreen()
  open()

  def updateContents() = {
    new BorderPanel {
      add(new FlowPanel {
        contents += new SizeOptionPanel(controller)
      }, BorderPanel.Position.North)

      add(new FieldGridPanel(controller), BorderPanel.Position.Center)
      add(new PlayerPanel(controller), BorderPanel.Position.West)
      add(new FlowPanel {
          contents += new ControlPanel(controller)
        }, BorderPanel.Position.South)
    }
  }

  // Player Panel
  class PlayerPanel(controller: Controller) extends BorderPanel {
    // Create a label for displaying text
    val playerInfoText = new BoxPanel(Orientation.Vertical) {
      contents += new Label(" Players") {
        font = new Font("SansSerif", 3, 20)
      }
      contents += new Label("")
      contents += new Label("  next up:") {
        font = new Font("SansSerif", 3, 16)
      }
    }

    layout(playerInfoText) = BorderPanel.Position.North

    val playersContainer = new BoxPanel(Orientation.Vertical)
    // Add other components if needed, e.g., buttons, images, etc.
    controller.game.queue.foreach { element =>
      val playerLayout = new FlowPanel {
        contents += new Button(element.name + ":" + element.position) {
          preferredSize = new Dimension(100,75)
        }
        contents += new DotPanel(element.color)
      }
      playersContainer.contents += playerLayout
    }
    layout(playersContainer) = BorderPanel.Position.Center
    preferredSize = new Dimension(110,300)
  }

  // Panel for creating the Game Size
  class SizeOptionPanel(controller: Controller) extends BoxPanel(Orientation.Vertical) {
    contents += new FlowPanel {
      contents += new Label("Select Board Size") {
        font = new Font("SansSerif", 3, 16)
      }
    }
    contents += new BoxPanel(Orientation.Horizontal) {
      contents += new BorderPanel {
        add(new Button(Action("4x4") {
          controller.create(4)
        }), BorderPanel.Position.Center)
        preferredSize = new Dimension(175, 50)
      }

      contents += new BorderPanel {
        add(new Button(Action("6x6") {
          controller.create(6)
        }), BorderPanel.Position.Center)
        preferredSize = new Dimension(175, 50)
      }

      contents += new BorderPanel {
        add(new Button(Action("8x8") {
          controller.create(8)
        }), BorderPanel.Position.Center)
        preferredSize = new Dimension(175, 50)
      }

      contents += new BorderPanel {
        add(new Button(Action("10x10") {
          controller.create(10)
        }), BorderPanel.Position.Center)
        preferredSize = new Dimension(175, 50)
      }
    }
  }

  class FieldGridPanel(controller: Controller) extends GridPanel(sqrt(controller.game.board.size).toInt, sqrt(controller.game.board.size).toInt) {
    contents ++= (1 to controller.game.board.size).map { i =>
        new FieldPanel(controller, i)
    }
  }

  class FieldPanel(controller: Controller, field: Int) extends BorderPanel {
    // field number
    val label = new Label(field.toString)

    // colors for player positions
    val playerDots = new BoxPanel(Orientation.Horizontal) {
      controller.game.queue.foreach { element =>
        if (element.position == field) {
          contents += new DotPanel(element.color)
        }
      }
    }

    // Load and scale images
    val snakeImage = new ImageIcon("SnakeIcon.png").getImage.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH)
    val ladderImage = new ImageIcon("LadderIcon.jpg").getImage.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH)
    // Blank image for cases where neither snakes nor ladders are present
    val blankImage = new ImageIcon("blankImage.png").getImage.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH)


    // Create JLabels for displaying images
    val snakeLabel = new Label {
      icon = new ImageIcon(snakeImage)
    }
    val ladderLabel = new Label {
      icon = new ImageIcon(ladderImage)
    }
    val blankLabel = new Label {
      icon = new ImageIcon(ladderImage)
    }
    val emptyLabel = new Label {
      preferredSize = new Dimension(25,25)
    }


    layout(label) = BorderPanel.Position.South
    layout(playerDots) = BorderPanel.Position.Center

    if (controller.game.board.snakes.contains(field)) {
      layout(snakeLabel) = BorderPanel.Position.North
    } else if (controller.game.board.ladders.contains(field)) {
      layout(ladderLabel) = BorderPanel.Position.North
    } else {
      layout(emptyLabel) = BorderPanel.Position.North
    }

    preferredSize = new Dimension(60, 60)
    border = BorderFactory.createLineBorder(Color.BLACK, 1)
  }

  class DotPanel(dotColor: Color) extends Panel {
    // Set the preferred size for the DotPanel
    preferredSize = new Dimension(20, 20)

    override def paintComponent(g: Graphics2D): Unit = {
      super.paintComponent(g)

      // Set the color for the dot
      g.setColor(dotColor)

      // Calculate the position and size of the dot
      val dotSize = Math.min(size.width, size.height)
      val dotX = (size.width - dotSize) / 2
      val dotY = (size.height - dotSize) / 2

      // Draw the dot
      g.fillOval(dotX, dotY, dotSize, dotSize)
    }
  }

  class ControlPanel(controller: Controller) extends BoxPanel(Orientation.Horizontal) {
    contents += new BorderPanel {
      add(new Button(Action("Roll") {
        controller.roll()
      }), BorderPanel.Position.Center)
      preferredSize = new Dimension(400, 50)
    }
    contents += new BorderPanel {
      add(new Button(Action("Undo") {
        controller.undo()
      }), BorderPanel.Position.Center)
      preferredSize = new Dimension(400, 50)
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
        contents = updateContents()
        repaint()
      case Event.AddPlayer =>
        contents = updateContents()
        repaint()
      case Event.Undo =>
        contents = updateContents()
        repaint()
      case Event.Roll =>
        contents = updateContents()
        repaint()
      case Event.Exit =>
  }
}


