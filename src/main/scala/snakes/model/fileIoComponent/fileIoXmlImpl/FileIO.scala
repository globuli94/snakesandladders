package snakes.model.fileIoComponent.fileIoXmlImpl

import snakes.model.boardComponent.Board
import snakes.model.fileIoComponent.FileIOInterface
import snakes.model.gameComponent.{Game, GameInterface}
import snakes.model.playerComponent.{Player, PlayerInterface}
import scala.collection.immutable.Queue
import scala.xml.{Elem, Node, PrettyPrinter, XML}
import java.awt.Color

class FileIO extends FileIOInterface {

  override def load: GameInterface = {
    val file = scala.xml.XML.loadFile("game.xml")
    val boardSizeStr = (file \ "boardSize").text
    val boardSize = if (boardSizeStr.nonEmpty) boardSizeStr.toInt else 100 // Default or error
    val players = (file \ "players" \ "player").map(node => {
      val name = (node \ "name").text
      val position = (node \ "position").text.toInt
      val lastRoll = (node \ "lastRoll").text.toInt
      val color = stringToColor((node \ "color").text)
      Player(name, position, color, lastRoll)
    })
    Game(Board.createBoard(boardSize), Queue(players: _*), gameStarted = true)
  }

  private def stringToColor(colorString: String): java.awt.Color = {
    val rgba = colorString.split(",").map(_.toInt)
    new java.awt.Color(rgba(0), rgba(1), rgba(2), rgba(3))
  }



  override def save(game: GameInterface): Unit = {
    val xml = gameToXml(game)
    val prettyPrinter = new PrettyPrinter(120, 4)
    val xmlString = prettyPrinter.format(xml)
    XML.save("game.xml", xml)
  }

  private def gameToXml(game: GameInterface): Elem = {
    <game>
      <board>
        <size>{game.getBoard.getSize}</size>
      </board>
      <players>
        {game.getPlayers.map(playerToXml)}
      </players>
      <gameStarted>{game.isGameStarted()}</gameStarted>
    </game>
  }

  private def playerToXml(player: PlayerInterface): Elem = {
    val color = player.getColor
    <player>
      <name>{player.getName}</name>
      <position>{player.getPosition}</position>
      <lastRoll>{player.getLastRoll}</lastRoll>
      <color>{color.getRed},{color.getGreen},{color.getBlue},{color.getAlpha}</color>
    </player>
  }

}