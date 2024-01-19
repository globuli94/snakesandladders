package snakes.aview

import scala.swing.*
import snakes.controller.ControllerInterface
import snakes.util.{Event, Observer}

import java.awt.Color
import scala.swing.MenuBar.NoMenuBar.revalidate

class GUI(controller: ControllerInterface) extends MainFrame with Observer {
  title = "Snakes and Ladders"

  minimumSize = new Dimension(1024, 768)
  preferredSize = new Dimension(1024, 768)
  maximumSize = new Dimension(1024, 768)
  resizable = false // This makes the window not resizable

  // Use modern-looking UI components and colors
  background = Color.lightGray
  foreground = Color.darkGray

  // Custom font style
  val customFont = new Font("Arial",0 , 14)

  // Initialize the main scene
  val mainScene = new MainScene(controller)
  val gameScene = new GameScene(controller)


  // Set up the main window's size and make it visible
  preferredSize = new Dimension(1200, 400)
  contents = mainScene

  // Listen to the close operation
  peer.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE)

  // Register this GUI as an observer to the controller
  controller.add(this)

  // Implement the update method from the Observer trait
  override def update(e: Event): Unit = {
    e match {
      case Event.Start =>
        // Here you would switch to the game scene
        contents = gameScene
        repaint()
        revalidate()
      case Event.Roll(rollResult) =>
      // Handle roll event if necessary, for example, updating the game scene
      case Event.Undo =>
      // Handle undo event if necessary
      case _ =>
      // Handle other events
    }
  }

  // Show the GUI
  visible = true
}