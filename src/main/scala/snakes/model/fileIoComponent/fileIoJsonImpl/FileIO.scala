package snakes.model.fileIoComponent.fileIoJsonImpl

import play.api.libs.functional.syntax.toFunctionalBuilderOps
import snakes.model.gameComponent.{Game, GameInterface}
import snakes.model.playerComponent.{Player, PlayerInterface}
import snakes.model.boardComponent.{Board, BoardInterface}
import play.api.libs.json.*
import snakes.model.fileIoComponent.FileIOInterface

import scala.io.Source
import java.io.PrintWriter
import scala.collection.immutable.Queue
import java.awt.Color

class FileIO extends FileIOInterface {

  implicit val playerWrites: Writes[PlayerInterface] = new Writes[PlayerInterface] {
    def writes(player: PlayerInterface): JsValue = Json.obj(
      "name" -> player.getName,
      "position" -> player.getPosition,
      "color" -> player.getColor.getRGB, // RGB value of the color
      "lastRoll" -> player.getLastRoll
    )
  }

  implicit val playerReads: Reads[PlayerInterface] = (
    (JsPath \ "name").read[String] and
      (JsPath \ "position").read[Int] and
      (JsPath \ "color").read[Int] and
      (JsPath \ "lastRoll").read[Int]
    )((name, position, color, lastRoll) =>
    Player(name, position, new Color(color), lastRoll)
  )

  implicit val gameWrites: Writes[GameInterface] = new Writes[GameInterface] {
    def writes(game: GameInterface): JsValue = Json.obj(
      "boardSize" -> game.getBoard.getSize,
      "players" -> game.getPlayers.map(Json.toJson(_)),
      "gameStarted" -> game.isGameStarted()
    )
  }

  implicit val gameReads: Reads[GameInterface] = (
    (JsPath \ "boardSize").read[Int] and
      (JsPath \ "players").read[Queue[PlayerInterface]] and
      (JsPath \ "gameStarted").read[Boolean]
    )((size, players, started) =>
    Game(Board.createBoard(size), players, started)
  )

  override def load: GameInterface = {
    val jsonString = Source.fromFile("game.json").getLines.mkString
    val json = Json.parse(jsonString)
    json.validate[GameInterface] match {
      case JsSuccess(game, _) => game
      case JsError(errors) =>
        throw new Exception("Error parsing JSON: " + errors.toString)
    }
  }

  override def save(game: GameInterface): Unit = {
    val jsonString = Json.stringify(Json.toJson(game))
    new PrintWriter("game.json") { write(jsonString); close() }
  }
}
