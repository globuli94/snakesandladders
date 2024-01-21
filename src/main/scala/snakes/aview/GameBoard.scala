package snakes.aview

import snakes.SnakesAndLadders.controller

import java.awt.geom.CubicCurve2D
import java.awt.{BasicStroke, Color, Font}
import scala.math.{pow, sqrt}
import scala.swing.MenuBar.NoMenuBar.preferredSize
import scala.swing.{Dimension, Graphics2D, Panel}

class GameBoard(var boardSize: Int, var ladders: Map[Int, Int] = Map(), var snakes: Map[Int, Int] = Map()) extends Panel {

  preferredSize = new Dimension(650, 650)

  var playerPositions: Map[String, (Int, Color)] = Map()
  def updatePlayerPositions(newPositions: Map[String, (Int, Color)]): Unit = {
    playerPositions = newPositions
    repaint()
  }

  def updateLadders(newLadders: Map[Int, Int]): Unit = {
    ladders = newLadders
    repaint()
  }

  def updateSnakes(newSnakes: Map[Int, Int]): Unit = {
    snakes = newSnakes
    repaint()
  }

  override def paintComponent(g: Graphics2D): Unit = {
    super.paintComponent(g)

    val cellSize = preferredSize.width / boardSize
    val fontMetrics = g.getFontMetrics(new Font("Arial", Font.BOLD, cellSize / 5))

    for (i <- 0 until boardSize * boardSize) {
      val x = i % boardSize
      val y = i / boardSize
      val cellX = if (y % 2 == 0) x else (boardSize - 1) - x
      val cellY = boardSize - 1 - y

      g.setColor(if ((cellX + cellY) % 2 == 0) new Color(0x3cB371) else new Color(0xADD8E6))
      g.fillRect(cellX * cellSize, cellY * cellSize, cellSize, cellSize)

      g.setColor(Color.BLACK)
      g.drawRect(cellX * cellSize, cellY * cellSize, cellSize, cellSize)

      val label = (i + 1).toString
      val labelWidth = fontMetrics.stringWidth(label)
      val labelHeight = fontMetrics.getAscent
      g.setFont(new Font("Arial", Font.BOLD, cellSize / 5))
      g.drawString(label, (cellX * cellSize) + (cellSize - labelWidth) / 2, (cellY * cellSize) + (cellSize + labelHeight) / 2)
    }
    val pieceRadius = preferredSize.width / boardSize * 0.3
    playerPositions.foreach { case (playerName, (position, color)) =>
      val (x, y) = getCellCoordinates(position)
      val centerX = x + cellSize / 2
      val centerY = y + cellSize / 2
      g.setColor(color)
      g.fillOval(centerX - pieceRadius.toInt, centerY - pieceRadius.toInt, (pieceRadius * 2).toInt, (pieceRadius * 2).toInt)
    }
    ladders.foreach { case (start, end) =>
      drawLadder(g, start, end)
    }
    snakes.foreach { case (start, end) =>
      drawSnake(g, start, end)
    }
  }

  import scala.math.min

  private def drawLadder(g: Graphics2D, start: Int, end: Int): Unit = {
    val offsetDistance = 12
    val rungSpacing = 20

    val (mainStartX, mainStartY) = getCellCenter(start)
    val (mainEndX, mainEndY) = getCellCenter(end)

    val dx = mainEndX - mainStartX
    val dy = mainEndY - mainStartY
    val distance = sqrt(pow(dx, 2) + pow(dy, 2))
    val offsetX = offsetDistance * dy / distance
    val offsetY = offsetDistance * dx / distance

    val (line1StartX, line1StartY) = (mainStartX - offsetX, mainStartY + offsetY)
    val (line1EndX, line1EndY) = (mainEndX - offsetX, mainEndY + offsetY)
    val (line2StartX, line2StartY) = (mainStartX + offsetX, mainStartY - offsetY)
    val (line2EndX, line2EndY) = (mainEndX + offsetX, mainEndY - offsetY)

    val ladderColor = new Color(0xda8155)
    g.setColor(ladderColor)
    g.setStroke(new BasicStroke(6))

    g.drawLine(line1StartX.toInt, line1StartY.toInt, line1EndX.toInt, line1EndY.toInt)
    g.drawLine(line2StartX.toInt, line2StartY.toInt, line2EndX.toInt, line2EndY.toInt)

    val numRungs = ((distance - rungSpacing) / rungSpacing).toInt
    for (i <- 1 to numRungs) {
      val t = min(1.0, i * rungSpacing / distance)
      val rungStartX = line1StartX + t * (line1EndX - line1StartX)
      val rungStartY = line1StartY + t * (line1EndY - line1StartY)
      val rungEndX = line2StartX + t * (line2EndX - line2StartX)
      val rungEndY = line2StartY + t * (line2EndY - line2StartY)

      g.drawLine(rungStartX.toInt, rungStartY.toInt, rungEndX.toInt, rungEndY.toInt)
    }

    g.setStroke(new BasicStroke(1))
  }

  private def drawSnake(g: Graphics2D, start: Int, end: Int): Unit = {
    val snakeColor = new Color(0x044404)
    g.setColor(snakeColor)
    val stroke = new BasicStroke(18, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)
    g.setStroke(stroke)

    val (startX, startY) = getCellCenter(start)
    val (endX, endY) = getCellCenter(end)

    val ctrlX1 = startX + (endX - startX) * 0.33
    val ctrlY1 = startY + (endY - startY) * 0.77
    val ctrlX2 = startX + (endX - startX) * 0.77
    val ctrlY2 = startY + (endY - startY) * 0.33

    val snakeCurve = new CubicCurve2D.Double(startX, startY, ctrlX1, ctrlY1, ctrlX2, ctrlY2, endX, endY)
    g.draw(snakeCurve)

    val eyeSize = 6
    val eyeOffset = 6

    val eye1X = startX - eyeOffset
    val eye1Y = startY - eyeOffset
    val eye2X = startX + eyeOffset
    val eye2Y = startY - eyeOffset

    g.setColor(Color.WHITE)
    g.fillOval(eye1X - eyeSize / 2, eye1Y - eyeSize / 2, eyeSize, eyeSize)
    g.fillOval(eye2X - eyeSize / 2, eye2Y - eyeSize / 2, eyeSize, eyeSize)

    g.setStroke(new BasicStroke(3))

    val smileWidth = 12
    val smileHeight = 6
    val smileX = startX - smileWidth / 2
    val smileY = startY + eyeOffset

    g.setColor(Color.WHITE)
    g.drawArc(smileX, smileY, smileWidth, smileHeight, 0, -180)

    g.setStroke(new BasicStroke(1))
  }


  private def getCellCenter(position: Int): (Int, Int) = {
    val cellSize = preferredSize.width / boardSize
    val (cellX, cellY) = getCellCoordinates(position)
    val centerX = (cellX + cellSize / 2)
    val centerY = cellY + cellSize / 2 - 16
    (centerX, centerY)
  }

  private def getCellCoordinates(position: Int): (Int, Int) = {
    val cellSize = preferredSize.width / boardSize
    val row = (position - 1) / boardSize
    val col = if (row % 2 == 0) (position - 1) % boardSize else boardSize - 1 - ((position - 1) % boardSize)

    val x = col * cellSize
    val y = (boardSize - 1 - row) * cellSize

    (x, y)
  }


  def updateBoardSize(newSize: Int): Unit = {
    boardSize = newSize
    repaint()
  }
}
