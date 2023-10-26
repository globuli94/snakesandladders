@main def main(): Unit = {
  val game = GameController().createGame();
  println(game.players)
}

