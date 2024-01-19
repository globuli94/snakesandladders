package snakes.aview

import snakes.controller.ControllerInterface
import snakes.util.{Event, Observer}

import scala.swing.*
import scala.swing.event.*
import javax.swing.border.EmptyBorder
import java.awt.{Color, Dimension, Font, Insets}

class MainScene(controller: ControllerInterface) extends BoxPanel(Orientation.Vertical) with Observer {

  contents.foreach {
    case button: Button => button.action = Action(button.text) {
      val size = button.text.split("x")(0).toInt // Get the size from the button label
      controller.createGame(size) // Call createGame on the controller
    }
  }



  controller.add(this)

  // Define colors
  val poolTableGreen = new Color(0x0e5932)
  val darkBlue = new Color(0x29397e)
  val buttonFillColor = new Color(0xa8d0e5)
  val buttonTextColor = Color.BLACK

  background = poolTableGreen
  opaque = true

  // Welcome message
  contents += new BoxPanel(Orientation.Vertical) {
    contents += new Label("Snakes and Ladders") {
      font = new Font("Arial", Font.BOLD, 48)
      foreground = Color.WHITE
    }
    background = poolTableGreen
    border = new EmptyBorder(50, 0, 100, 0)
  }

  // Board size selection label
  val boardSizeLabel = new Label("Recommended Board Sizes") {
    font = new Font("Arial", Font.BOLD, 28)
    background = darkBlue
    foreground = Color.WHITE
    border = new EmptyBorder(10, 50, 10, 50)
    opaque = true
  }

  // Board size buttons
  // Board size buttons
  val boardSizePanel = new GridPanel(1, 4) {
    hGap = 5
    vGap = 5
    List("6x6", "8x8", "10x10").foreach { boardSizeLabel =>
      contents += new Button(boardSizeLabel) {
        font = new Font("Arial", Font.BOLD, 18)
        background = buttonFillColor
        foreground = buttonTextColor
        preferredSize = new Dimension(80, 40)
        action = Action(boardSizeLabel) {
          val boardSize = boardSizeLabel.split("x")(0).toInt
          controller.createGame(boardSize) // Use the existing createGame method
        }
      }
    }
    background = poolTableGreen
    maximumSize = new Dimension(450, 60)
  }
  val boardSizePanel2 = new GridPanel(1, 4) {
    hGap = 5
    vGap = 5
    List("", "").map(createSizeButton).foreach(contents += _)
    background = poolTableGreen
    border = Swing.EmptyBorder(0, 0, 80, 0) // Increased bottom padding
    maximumSize = new Dimension(450, 60) // Set a smaller size for the buttons
  }

  // Player addition
  val addPlayerLabel = new Label("Add players:") {
    font = new Font("Arial", Font.BOLD, 28)
    background = darkBlue
    foreground = Color.WHITE
    border = new EmptyBorder(10, 50, 10, 50)
    opaque = true
  }

  val playerTextField = new TextField {
    columns = 20
    font = new Font("Arial", Font.PLAIN, 18)
    maximumSize = new Dimension(preferredSize.width, 30) // Set the preferred height to 30

  }

  val addPlayerButton = new Button("Add") {
    font = new Font("Arial", Font.BOLD, 18)
    background = buttonFillColor
    foreground = buttonTextColor
    //border = Swing.LineBorder(darkBlue, 2, true)
  }
  val boardSizePanel3 = new GridPanel(1, 4) {
    hGap = 5
    vGap = 5
    List("", "").map(createSizeButton).foreach(contents += _)
    background = poolTableGreen
    border = Swing.EmptyBorder(0, 0, 80, 0) // Increased bottom padding
    maximumSize = new Dimension(450, 60) // Set a smaller size for the buttons
  }

  // Start game button
  val startButton = new Button("Start") {
    font = new Font("Arial", Font.BOLD, 32)
    background = buttonFillColor
    foreground = buttonTextColor
    //border = Swing.LineBorder(darkBlue, 2, true)

  }

  // Add components to MainScene
  contents ++= Seq(boardSizeLabel, boardSizePanel, boardSizePanel2, addPlayerLabel, playerTextField, addPlayerButton,boardSizePanel3, startButton)
  contents.foreach(_.xLayoutAlignment = 0.5)

  // Custom button creation method
  private def createSizeButton(label: String): Button = new Button(label) {
    font = new Font("Arial", Font.BOLD, 18)
    background = buttonFillColor
    foreground = buttonTextColor
    //border = Swing.LineBorder(darkBlue, 2, true)
    preferredSize = new Dimension(80, 40)
  }


  addPlayerButton.action = Action("Add") {
    controller.addPlayer(playerTextField.text)
    playerTextField.text = ""
  }

  startButton.action = Action("Start") {
    controller.startGame()
  }

  // Implement the Observer update method
  override def update(e: Event): Unit = {
    e match {
      case Event.Create => Dialog.showMessage(contents.head, "A new board has been created.")
      case Event.AddPlayer => Dialog.showMessage(contents.head, "A new player has been added.")
      case Event.Start => switchToGameScene()

      // Handle other events as necessary
      case _ => // Other events can be handled here
    }
  }

  // Method to switch to the game scene
  def switchToGameScene(): Unit = {
    // Implement scene switching logic here
  }

  // Styling the MainScene
  border = Swing.EmptyBorder(10)
  preferredSize = new Dimension(1024, 768)
}