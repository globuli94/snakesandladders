
import snakes.model.fileIoComponent.fileIoXmlImpl.FileIO

object FileIoXmlWorksheet extends App {
  val fileIO = new FileIO()
  val game = fileIO.load
  println(game)

  fileIO.save(game)
}
