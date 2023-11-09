package snakes.model


case class Dice() {
  def roll(): Int = scala.util.Random.nextInt(6) + 1
}