package snakes.aview

import scala.swing.*
import scala.swing.event.*
import java.awt.{Color, Dimension, Toolkit}
import snakes.controller.{Controller, RollCommand}
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
  val welcomeLabel = new Label("Snakes and Ladders!") {
    font = new Font("Arial", java.awt.Font.BOLD, 18)
  }
  val boardSizeLabel = new Label("Board Size:") {
    font = new Font("Arial", java.awt.Font.PLAIN, 12)
  }
  val customSizeTextField = new TextField {
    preferredSize = new Dimension(120, 30)
    border = BorderFactory.createLineBorder(borderColor)
  }
  val playerNameLabel = new Label("Player Name") {
    font = new Font("Arial", java.awt.Font.PLAIN, 12)
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
  val playerInfoPanel = new BoxPanel(Orientation.Vertical) {
    preferredSize = new Dimension(200, 400)
    border = BorderFactory.createTitledBorder("Players")
  }
  val createButton = new Button("Create") {
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
  val rollButton = new Button("Roll") {
    background = buttonColor
    foreground = textColor
    preferredSize = new Dimension(120, 30)
    borderPainted = false
    opaque = true
    visible = false
  }
  val undoButton = new Button("Undo") {
    background = buttonColor
    foreground = textColor
    preferredSize = new Dimension(120, 30)
    borderPainted = false
    opaque = true
    visible = false
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

    val snakes = controller.getSnakes // Map where key is the head of the snake
    val ladders = controller.getLadders // Map where key is the start of the ladder

    val totalSquares = boardSize * boardSize
    for (i <- 1 to totalSquares) {
      val colorIndex = (i - 1) % 4
      val squarePanel = new BoxPanel(Orientation.Vertical) {
        background = if (i % 2 == 0) greens(colorIndex) else blues(colorIndex)
        preferredSize = new Dimension(50, 50)
        opaque = true
      }

      // Number label
      val numberLabel = new Label(i.toString) {
        foreground = Color.BLACK
        font = new Font("Arial", java.awt.Font.BOLD, if (boardSize < 10) 18 else 12)
      }
      squarePanel.contents += numberLabel

      // Marker label for snake or ladder
      val markerLabel = new Label {
        foreground = Color.BLACK
        font = new Font("Arial", java.awt.Font.BOLD, if (boardSize < 10) 18 else 12)
        text = (snakes.get(i).map(_ => "X") orElse ladders.get(i).map(_ => "O")).getOrElse("")
        opaque = false
      }
      if (markerLabel.text.nonEmpty) {
        squarePanel.contents += markerLabel
      }

      boardPanel.contents += squarePanel
    }

    boardPanel.revalidate()
    boardPanel.repaint()
  }

  def updatePlayerInfo(): Unit = {
    playerInfoPanel.contents.clear()
    val queue = controller.game.queue
    val currentPlayer = queue.head
    val otherPlayers = queue.tail

    // Display current player with position
    val currentPlayerLabel = new Label(s"${currentPlayer.name} (Position: ${currentPlayer.position})") {
      foreground = Color.RED // Highlight current player, for example
      font = new Font("Arial", java.awt.Font.BOLD, 12)
    }
    playerInfoPanel.contents += currentPlayerLabel

    // Display other players with position
    otherPlayers.foreach { player =>
      val playerLabel = new Label(s"${player.name} (Position: ${player.position})") {
        foreground = Color.BLUE // Standard color for other players
        font = new Font("Arial", java.awt.Font.PLAIN, 12)
      }
      playerInfoPanel.contents += playerLabel
    }

    playerInfoPanel.revalidate()
    playerInfoPanel.repaint()
  }


  // Observer update method
  def update: Unit = {
    val currentBoardSize = controller.game.board.size
    updateBoard(currentBoardSize)
    updatePlayerInfo()

  }

  // Layout
  contents = new BorderPanel {
    layout(welcomeLabel) = BorderPanel.Position.North
    layout(boardPanel) = BorderPanel.Position.Center
    layout(playerInfoPanel) = BorderPanel.Position.East
    layout(new BoxPanel(Orientation.Vertical) {
      contents += new FlowPanel(boardSizeLabel, customSizeTextField, createButton)
      contents += new FlowPanel(playerNameLabel, playerNameTextField, addPlayerButton)
      contents += new FlowPanel(rollButton, undoButton)
      contents += startButton
      border = Swing.EmptyBorder(10)
    }) = BorderPanel.Position.South
  }

  // Event Listeners
  listenTo(addPlayerButton, startButton)
  reactions += {
    case ButtonClicked(`addPlayerButton`) if playerNameTextField.text.nonEmpty =>
      controller.addPlayer(playerNameTextField.text)
      playerNameTextField.text = "" // Clear the text field
      updatePlayerInfo() // Update player info panel

    case ButtonClicked(`startButton`) =>
      controller.start
      // Hide setup components
      customSizeTextField.visible = false
      playerNameTextField.visible = false
      addPlayerButton.visible = false
      createButton.visible = false
      startButton.visible = false
      boardSizeLabel.visible = false
      playerNameLabel.visible = false

      // Show game components
      rollButton.visible = true
      undoButton.visible = true
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
  listenTo(rollButton, undoButton)
  reactions += {
    case ButtonClicked(`rollButton`) =>
      controller.roll
      controller.saveState
      updatePlayerInfo() // Update player info panel after rolling

    case ButtonClicked(`undoButton`) =>
      controller.undo
    // Update the board or any other components as needed
  }
  centerOnScreen()
  open()
}