@main def main(): Unit = {
  val board = GameController().createBoard(100)
  println(board.size)
}

