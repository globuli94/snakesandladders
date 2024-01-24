package snakes.aview

import scala.swing.*
import scala.swing.event.*
import snakes.controller.ControllerInterface
import snakes.util.Event.Load
import snakes.util.{Event, Observer}

import java.awt.{Color, Font, RenderingHints}
import javax.sound.sampled._
import javax.swing.{ImageIcon, Timer}
import javax.swing.border.{EmptyBorder, LineBorder, TitledBorder}
import scala.swing.BorderPanel.Position.Center

class GameScene(controller: ControllerInterface) extends BorderPanel with Observer {

  controller.add(this)

  val poolTableGreen = new Color(0x0e5932)
  val darkBlue = new Color(0x29397e)
  val buttonFillColor = new Color(0xa8d0e5)
  val buttonTextColor = Color.BLACK
  val lightGray = new Color(0xd3d3d3)


  background = poolTableGreen
  opaque = true

  val diceImageIcon = new ImageIcon("src/resources/dice_5.png")
  val diceImageLabel = new Label {
    icon = diceImageIcon
    preferredSize = new Dimension(200, 200)
  }
  val rollButton = new Button("Roll") {
    maximumSize = new Dimension(200, 50)
    background = buttonFillColor
    foreground = buttonTextColor
    font = new Font("Arial", Font.BOLD, 18)
    listenTo(this)
    reactions += {
      case ButtonClicked(_) =>
        controller.rollDice()
    }
  }
  val leftPanel = new BoxPanel(Orientation.Vertical) {
    contents += rollButton
    contents += Swing.VStrut(20)
    contents += diceImageLabel
    contents += Swing.VStrut(100)
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
    preferredSize = new Dimension(650, 650)
    minimumSize = new Dimension(650, 650)
    maximumSize = new Dimension(650, 650)
    opaque = false
  } // default starting size


  // Game board placeholder
  val gameBoardPlaceholder = new BoxPanel(Orientation.Vertical) {
    preferredSize = new Dimension(650, 650)
    minimumSize = new Dimension(650, 650)
    maximumSize = new Dimension(650, 650)

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
    contents += new Button(Action("Return") {
      controller.restartGame()
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

  val playersPanel = new BoxPanel(Orientation.Vertical) {
    contents += playersListLabel
    contents += Swing.VStrut(20)
    contents += playersList
    contents += Swing.VStrut(20)
    contents += buttonsPanel
    border = Swing.EmptyBorder(0, 10, 10, 10)
    opaque = false
  }

  val horizontalPanel = new BoxPanel(Orientation.Horizontal) {
    contents += Swing.HStrut(20)
    contents += leftPanel
    contents += gameBoard
    contents += Swing.HStrut(20)
    contents += playersPanel
    background = poolTableGreen
    opaque = true
  }

  layout(horizontalPanel) = Center



  class ColorDot(color: Color) extends Panel {
    minimumSize = new Dimension(12, 12)
    maximumSize = new Dimension(12, 12)
    opaque = false

    override def paintComponent(g: Graphics2D): Unit = {
      super.paintComponent(g)
      g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
      g.setColor(color)
      g.fillOval(0, 0, size.width, size.height)
      opaque = false
    }
  }

  def setBoardSize(size: Int): Unit = {
    gameBoard.updateBoardSize(size)
  }

  def updateBoardSize(size: Int): Unit = {
    gameBoard.updateBoardSize(size)
  }

  private def updateDiceImage(rollResult: Int): Unit = {
    val diceImagePath = s"src/resources/dice_$rollResult.png"
    diceImageLabel.icon = new ImageIcon(diceImagePath)
    diceImageLabel.repaint()
  }

  private def updatePlayerList(): Unit = {
    playersList.contents.clear()
    controller.getCurrentGameState.getPlayers.foreach { player =>

      playersList.contents += new Label(player.getName) {
        font = new Font("Arial", Font.BOLD, 22)
        foreground = Color.WHITE
        playersList.contents += new ColorDot(player.getColor)
      }

      playersList.contents += Swing.VStrut(18)
    }
    playersList.revalidate()
    playersList.repaint()
  }



  def showDiceAnimationThenResult(rollResult: Int): Unit = {
    var currentImageIndex = 1
    val totalDuration = 500
    val interval = totalDuration / 6

    val timer = new Timer(interval, null)
    timer.addActionListener(new java.awt.event.ActionListener {
      def actionPerformed(e: java.awt.event.ActionEvent): Unit = {
        if (currentImageIndex <= 6) {
          updateDiceImage(currentImageIndex)
          currentImageIndex += 1
        }
        if (currentImageIndex > 6) {
          timer.stop()
          updateDiceImage(rollResult)
          updatePlayerPositions()
        }
      }
    })
    timer.start()
  }



  def winMenu(): Unit = {
    if (controller.checkWin()) {
      val winningPlayer = controller.getCurrentGameState.getPlayers.find(_.getPosition == controller.getBoardSize).get
      val response = Dialog.showConfirmation(contents.head,
        s"Player ${winningPlayer.getName} has won!\nDo you want to return to main menu?",
        title = "Game Over",
        optionType = Dialog.Options.YesNo)

      if (response == Dialog.Result.Yes) {
        controller.restartGame()
      }
    }
  }


  def updatePlayerPositions(): Unit = {
    val players = controller.getCurrentGameState.getPlayers
    val positions = players.map { player =>
      player.getName -> (player.getPosition, player.getColor)
    }.toMap
    gameBoard.updatePlayerPositions(positions)
  }



  override def update(e: Event): Unit = {
    e match {
      case Event.Create =>
        updateBoardSize(Math.sqrt(controller.getBoardSize).toInt)
        val ladders = controller.getCurrentGameState.getBoard.getLadders
        gameBoard.updateLadders(ladders)
        val snakes = controller.getCurrentGameState.getBoard.getSnakes
        gameBoard.updateSnakes(snakes)

      case Event.Roll(rollResult) =>
        showDiceAnimationThenResult(rollResult)
        updatePlayerList()
        winMenu()

      case Event.AddPlayer | Event.Undo | Event.Load =>
        updatePlayerList()
        updatePlayerPositions()
      case _ =>
    }
  }
}