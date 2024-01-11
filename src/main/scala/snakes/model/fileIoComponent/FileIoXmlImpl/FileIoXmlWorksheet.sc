import snakes.model.fileIoComponent.FileIoXmlImpl.FileIO

object FileIoXmlWorksheet extends App {
  val fileIO = new FileIO()
  val game = fileIO.load
  println(game)

  fileIO.save(game)
}
