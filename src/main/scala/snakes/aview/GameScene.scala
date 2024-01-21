package snakes.aview

import scala.swing.*
import scala.swing.event.*
import snakes.controller.ControllerInterface
import snakes.util.Event.Load
import snakes.util.{Event, Observer}

import java.awt.{Color, Font, RenderingHints}
import javax.swing.ImageIcon
import javax.swing.border.{EmptyBorder, LineBorder, TitledBorder}
import scala.swing.BorderPanel.Position.Center

class GameScene(controller: ControllerInterface) extends BorderPanel with Observer {

  controller.add(this)

  // Define colors
  val poolTableGreen = new Color(0x0e5932)
  val darkBlue = new Color(0x29397e)
  val buttonFillColor = new Color(0xa8d0e5)
  val buttonTextColor = Color.BLACK
  val lightGray = new Color(0xd3d3d3)


  background = poolTableGreen
  opaque = true

  // Roll button

  // Photo placeholder
  val diceImageIcon = new ImageIcon("dice_5.png")
  val diceImageLabel = new Label {
    icon = diceImageIcon
    preferredSize = new Dimension(200, 200)
  }
  // Roll button with action listener
  val rollButton = new Button("Roll") {
    maximumSize = new Dimension(200, 50)
    background = buttonFillColor
    foreground = buttonTextColor
    font = new Font("Arial", Font.BOLD, 18)
    listenTo(this)
    reactions += {
      case ButtonClicked(_) =>
        controller.rollDice() // Call the rollDice method on the controller
    }
  }
  // Panel for roll button and photo placeholder
  val leftPanel = new BoxPanel(Orientation.Vertical) {
    contents += rollButton
    contents += Swing.VStrut(20) // Space between button and image
    contents += diceImageLabel // Add the dice image label here
    contents += Swing.VStrut(100) // Space between button and image
    contents += new Button("Undo") {
      action = Action("Undo") {
        controller.undoLastAction()
      }
      maximumSize = new Dimension(200, 50)
      background = buttonFillColor
      foreground = buttonTextColor
      font = new Font("Arial", Font.BOLD, 18)
    }
    opaque = false
  }

  val gameBoard = new GameBoard(10) {
    preferredSize = new Dimension(650, 650) // Ensure this size is large enough to be visible
    minimumSize = new Dimension(650, 650) // Add minimum size to ensure the component doesn't get too small
    maximumSize = new Dimension(650, 650) // Add maximum size for consistency
    opaque = false
  } // default starting size


  // Game board placeholder
  val gameBoardPlaceholder = new BoxPanel(Orientation.Vertical) {
    preferredSize = new Dimension(650, 650)  // Ensure this size is large enough to be visible
    minimumSize = new Dimension(650, 650)   // Add minimum size to ensure the component doesn't get too small
    maximumSize = new Dimension(650, 650)   // Add maximum size for consistency

    background = darkBlue
    opaque = true
    border = new LineBorder(Color.BLACK, 2)

  }

  // Players list label
  val playersListLabel = new Label("Players up next") {
    font = new Font("Arial", Font.BOLD, 24)
    background = darkBlue
    minimumSize = new Dimension(200, 60)
    maximumSize = new Dimension(200, 60)
    foreground = Color.WHITE
    opaque = true
  }

  // Players list placeholder
  val playersList = new BoxPanel(Orientation.Vertical) {
    preferredSize = new Dimension(200, (650 * 0.4).toInt)
    maximumSize = new Dimension(200, (650 * 0.4).toInt)
    minimumSize = new Dimension(200, (650 * 0.4).toInt)

    background = poolTableGreen
    opaque = true
    font = new Font("Arial", Font.BOLD, 22)
  }
  // Buttons panel
  val buttonsPanel = new BoxPanel(Orientation.Vertical) {
    contents += new Button(Action("Save") {
      controller.saveGame()
    }) {
      maximumSize = new Dimension(200, 50)
      background = buttonFillColor
      foreground = buttonTextColor
      font = new Font("Arial", Font.BOLD, 18)
    }
    contents += new Button(Action("Load") {
      controller.loadGame()
    }) {
      maximumSize = new Dimension(200, 50)
      background = buttonFillColor
      foreground = buttonTextColor
      font = new Font("Arial", Font.BOLD, 18)
    }
    contents += new Button(Action("Exit") {
      controller.exitGame()
    }) {
      maximumSize = new Dimension(200, 50)
      background = buttonFillColor
      foreground = buttonTextColor
      font = new Font("Arial", Font.BOLD, 18)
    }
    opaque = false
  }

  // Panel to hold the label, the list placeholder, and the buttons
  val playersPanel = new BoxPanel(Orientation.Vertical) {
    contents += playersListLabel
    contents += Swing.VStrut(20) // Add some vertical space before buttons
    contents += playersList
    contents += Swing.VStrut(20) // Add some vertical space before buttons
    contents += buttonsPanel
    border = Swing.EmptyBorder(0, 10, 10, 10) // Add some space around
    opaque = false // This panel should not paint a background
  }

  // Horizontal panel to arrange left panel, game board and players panel side by side
  val horizontalPanel = new BoxPanel(Orientation.Horizontal) {
    contents += Swing.HStrut(20) // Space between left panel and game board
    contents += leftPanel
    contents += gameBoard
    contents += Swing.HStrut(20) // Space between game board and players panel
    contents += playersPanel
    background = poolTableGreen
    opaque = true // This panel should paint the background
  }

  // Add the horizontal panel to the GameScene
  layout(horizontalPanel) = Center




  // Custom Panel to draw a colored dot
  class ColorDot(color: Color) extends Panel {
    minimumSize = new Dimension(12, 12) // Small, fixed size for the dot
    maximumSize = new Dimension(12, 12)
    opaque = false

    override def paintComponent(g: Graphics2D): Unit = {
      super.paintComponent(g)
      g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
      g.setColor(color)
      g.fillOval(0, 0, size.width, size.height) // Draw the dot
      opaque = false
    }
  }

  def setBoardSize(size: Int): Unit = {
    gameBoard.updateBoardSize(size)
  }

  def updateBoardSize(size: Int): Unit = {
    gameBoard.updateBoardSize(size)
  }

  // Helper method to update the dice image
  private def updateDiceImage(rollResult: Int): Unit = {
    val diceImagePath = s"dice_$rollResult.png"
    diceImageLabel.icon = new ImageIcon(diceImagePath)
    diceImageLabel.repaint()
  }

  // Method to update the player list
  private def updatePlayerList(): Unit = {
    playersList.contents.clear()
    controller.getCurrentGameState.getPlayers.foreach { player =>
      // Add a label for each player
      playersList.contents += new Label(player.getName) {
        font = new Font("Arial", Font.BOLD, 22)
        foreground = Color.WHITE
        playersList.contents += new ColorDot(player.getColor)
      }

      // Add space after each label
      playersList.contents += Swing.VStrut(18) // 10 pixels of vertical space
    }
    playersList.revalidate()
    playersList.repaint()
  }

  def animatePlayerMovement(rollResult: Int): Unit = {
    val currentPlayer = controller.getCurrentGameState.getCurrentPlayer()
    val oldPosition = currentPlayer.getPosition - rollResult
    var animationPosition = oldPosition

    val timer = new javax.swing.Timer(300, null)
    timer.addActionListener(new java.awt.event.ActionListener {
      def actionPerformed(e: java.awt.event.ActionEvent): Unit = {
        if (animationPosition < currentPlayer.getPosition) {
          animationPosition += 1
        } else if (animationPosition > currentPlayer.getPosition) {
          animationPosition -= 1
        } else {
          timer.stop()
        }

        // Update the player's position for animation
        val updatedPositions = gameBoard.playerPositions.updated(
          currentPlayer.getName, (animationPosition, currentPlayer.getColor)
        )
        gameBoard.updatePlayerPositions(updatedPositions)
      }
    })
    timer.start()
  }

  def winMenu(): Unit = {
    if (controller.checkWin()) {
      val winningPlayer = controller.getCurrentGameState.getPlayers.find(_.getPosition == controller.getBoardSize).get
      Dialog.showMessage(contents.head, s"Player ${winningPlayer.getName} has won!", title = "Game Over")
    }
  }



  def updatePlayerPositions(): Unit = {
    val players = controller.getCurrentGameState.getPlayers
    val positions = players.map { player =>
      player.getName -> (player.getPosition, player.getColor)
    }.toMap
    gameBoard.updatePlayerPositions(positions)
  }


  // Update method for Observer
  override def update(e: Event): Unit = {
    e match {
      case Event.Create =>
        // When a new game is created, update the board size
        // Assuming getBoardSize will give you the size (width) of the board
        updateBoardSize(Math.sqrt(controller.getBoardSize).toInt)
        val ladders = controller.getCurrentGameState.getBoard.getLadders
        gameBoard.updateLadders(ladders)
        val snakes = controller.getCurrentGameState.getBoard.getSnakes
        gameBoard.updateSnakes(snakes)

      case Event.Roll(rollResult) =>
        updateDiceImage(rollResult)
        updatePlayerList() // Update player list on roll
        updatePlayerPositions()
        winMenu()


      case Event.AddPlayer | Event.Undo | Event.Load =>
        updatePlayerList() // Update player list on add player, undo, and load
        updatePlayerPositions()
      case _ =>
    }
  }
}