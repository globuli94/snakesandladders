package snakes.aview

import scala.swing.*
import scala.swing.event.*
import java.awt.{Color, Dimension, Toolkit}
import snakes.controller.Controller
import snakes.util.Observer

import javax.swing.BorderFactory
import scala.swing.MenuBar.NoMenuBar.revalidate

class SwingGUI(controller: Controller) extends MainFrame {


  title = "Snakes and Ladders"
  preferredSize = new Dimension(800, 600)

  //colors
  val backgroundColor = new Color(107, 207, 200)
  val buttonColor = new Color(32, 161, 99)
  val textColor = Color.WHITE
  val borderColor = Color.BLACK
  val labelColor = Color.BLACK
  val greens = Array(new Color(70, 200, 70), new Color(50, 150, 100), new Color(50, 200, 140), new Color(70, 200, 120))
  val blues = Array(new Color(10, 70, 200), new Color(0, 100, 150), new Color(20, 100, 200), new Color(0, 40, 170))

  //  components
  val welcomeLabel = new Label("Welcome to Snakes and Ladders!") {
    font = new Font("Arial", java.awt.Font.BOLD, 18)
  }
  val boardSizeLabel = new Label("Board Size:") {
    font = new Font("Arial", java.awt.Font.PLAIN, 12)
  }
  val customSizeTextField = new TextField {
    preferredSize = new Dimension(120, 30)
    border = BorderFactory.createLineBorder(borderColor)
  }
  val playerNameTextField = new TextField {
    preferredSize = new Dimension(120, 30)
    border = BorderFactory.createLineBorder(borderColor)
  }
  val addPlayerButton = new Button("Add") {
    background = buttonColor
    foreground = textColor
    preferredSize = new Dimension(120, 30)
    borderPainted = false
    opaque = true
  }
  val createButton = new Button("Create Board") {
    background = buttonColor
    foreground = textColor
    preferredSize = new Dimension(120, 30)
    borderPainted = false
    opaque = true
  }
  val startButton = new Button("Start") {
    background = buttonColor
    foreground = textColor
    preferredSize = new Dimension(120, 30)
    borderPainted = false
    opaque = true
  }
  val boardPanel = new GridPanel(8, 8) {
    preferredSize = new Dimension(400, 400)
    border = BorderFactory.createLineBorder(Color.BLACK)
  }

  //update the board
  def updateBoard(boardSize: Int): Unit = {
    boardPanel.rows = boardSize
    boardPanel.columns = boardSize
    boardPanel.contents.clear()

    val totalSquares = boardSize * boardSize
    for (i <- 1 to totalSquares) {
      val colorIndex = (i - 1) % 4
      val squarePanel = new BorderPanel {
        background = if (i % 2 == 0) greens(colorIndex) else blues(colorIndex)
        preferredSize = new Dimension(50, 50) // Adjust the size based on actual requirements
      }

      // square numbers
      val label = new Label(i.toString) {
        foreground = Color.BLACK
        font = new Font("Arial", java.awt.Font.BOLD, if (boardSize < 10) 18 else 12)
        opaque = false
      }
      squarePanel.layout(label) = BorderPanel.Position.Center
      boardPanel.contents += squarePanel
    }

    boardPanel.revalidate()
    boardPanel.repaint()
  }

  // Observer update method
  def update: Unit = {
    val currentBoardSize = controller.game.board.size
    updateBoard(currentBoardSize)
  }

  // Layout
  contents = new BorderPanel {
    layout(welcomeLabel) = BorderPanel.Position.North
    layout(boardPanel) = BorderPanel.Position.Center
    layout(new BoxPanel(Orientation.Vertical) {
      contents += new FlowPanel(boardSizeLabel, customSizeTextField, createButton)
      contents += new FlowPanel(new Label("Player Name:"), playerNameTextField, addPlayerButton)
      contents += startButton
      border = Swing.EmptyBorder(10)
    }) = BorderPanel.Position.South
  }

  // Event Listeners
  listenTo(addPlayerButton, startButton)
  reactions += {
    case ButtonClicked(`addPlayerButton`) =>
      controller.addPlayer(playerNameTextField.text)
      playerNameTextField.text = ""

    case ButtonClicked(`startButton`) =>
      controller.start
  }
  listenTo(createButton)
  reactions += {
    case ButtonClicked(`createButton`) =>
      val size = customSizeTextField.text match {
        case str if str.nonEmpty => str.toInt
      }
      controller.create(size)
      updateBoard(size)
  }
  centerOnScreen()
  open()
}
