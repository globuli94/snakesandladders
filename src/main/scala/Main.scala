import scala.util.Random

case class GameConfig(boardSize: Int, snakesAndLadders: Map[Int, Int], numPlayers: Int)
case class GameState(playerPositions: List[Int], currentPlayer: Int)

object SnakesAndLaddersGame {
  private val boardSize = 10
  private val snakesAndLadders = Map(
    3 -> 22,
    5 -> 8,
    11 -> 26,
    20 -> 29,
    27 -> 1,
    21 -> 9,
    17 -> 4,
    19 -> 7
  )

  def main(args: Array[String]): Unit = {
    val numPlayers = getNumberOfPlayers()
    val initialState = GameState(List.fill(numPlayers)(1), 0)
    val config = GameConfig(boardSize, snakesAndLadders, numPlayers)

    playGame(config, initialState)
  }

  def playGame(config: GameConfig, state: GameState): Unit = {
    if (state.playerPositions(state.currentPlayer) >= config.boardSize * config.boardSize) {
      println(s"Congratulations! Player ${state.currentPlayer + 1} won the game!")
    } else {
      val diceRoll = getDiceRollForPlayer(state.currentPlayer)
      val newPosition = state.playerPositions(state.currentPlayer) + diceRoll
      println(s"Player ${state.currentPlayer + 1} moves from position ${state.playerPositions(state.currentPlayer)} to position $newPosition.")

      config.snakesAndLadders.get(newPosition) match {
        case Some(finalPosition) if finalPosition > newPosition =>
          println(s"Ladder! Player ${state.currentPlayer + 1} climbs to position $finalPosition.")
        case Some(finalPosition) =>
          println(s"Snake! Player ${state.currentPlayer + 1} slides down to position $finalPosition.")
        case None => // No snake or ladder encountered
      }

      val updatedPosition = config.snakesAndLadders.getOrElse(newPosition, newPosition)
      val updatedPositions = state.playerPositions.updated(state.currentPlayer, updatedPosition)
      val nextPlayer = (state.currentPlayer + 1) % config.numPlayers

      playGame(config, GameState(updatedPositions, nextPlayer))
    }
  }


  def getNumberOfPlayers(): Int = {
    println("How many players are playing? ")
    scala.io.StdIn.readInt()
  }

  def getDiceRollForPlayer(player: Int): Int = {
    println(s"\nPlayer ${player + 1}'s turn. Press Enter to roll the dice...")
    scala.io.StdIn.readLine()
    val diceRoll = rollDice()
    println(s"Player ${player + 1} rolled a $diceRoll")
    diceRoll
  }

  def rollDice(): Int = Random.nextInt(6) + 1
}
