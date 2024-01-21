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
  resizable = false

  background = Color.lightGray
  foreground = Color.darkGray

  val customFont = new Font("Arial",0 , 14)

  val mainScene = new MainScene(controller)
  val gameScene = new GameScene(controller)


  preferredSize = new Dimension(1200, 400)
  contents = mainScene

  peer.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE)

  controller.add(this)

  def showMainScene(): Unit = {
    contents = mainScene
  }

  def showGameScene(): Unit = {
    contents = gameScene
  }

  override def update(e: Event): Unit = {
    e match {
      case Event.Start =>
        showGameScene()
        repaint()
        revalidate()
      case Event.Restart => 
        showMainScene()
        repaint()
        revalidate()
      case _ =>
    }
  }

  visible = true
}