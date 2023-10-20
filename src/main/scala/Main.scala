object SnakesAndLaddersGame {
  def main(args: Array[String]): Unit = {
    //10x10 field
    val boardSize = 6
    val board = createGameBoard(boardSize)
    displayGameBoard(board)
  }

  def createGameBoard(size: Int): Array[Array[Int]] = {
    val board = Array.ofDim[Int](size, size)
    for {
      row <- 0 until size
      col <- 0 until size
    } {
      board(row)(col) = row * size + col + 1
    }
    board
  }

  def displayGameBoard(board: Array[Array[Int]]): Unit = {
    for (row <- board.indices) {
      for (col <- board(row).indices) {
        print(f"${board(row)(col)}%3d ")
      }
      println()
    }
  }
}
