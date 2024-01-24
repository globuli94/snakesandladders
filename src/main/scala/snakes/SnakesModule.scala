package snakes

import com.google.inject.AbstractModule
import net.codingwell.scalaguice.ScalaModule
import snakes.controller.{Controller, ControllerInterface}
import snakes.model.gameComponent.{GameInterface, Game}
import snakes.model.fileIoComponent.FileIOInterface
import snakes.model.fileIoComponent.fileIoXmlImpl.FileIO as XmlFileIO
import snakes.model.fileIoComponent.fileIoJsonImpl.FileIO as JsonFileIO

class SnakesModule extends AbstractModule with ScalaModule {
  override def configure(): Unit = {
    bind[GameInterface].toInstance(Game())
    bind[ControllerInterface].to[Controller]

    val useJson = System.getProperty("fileio.json", "false").toBoolean
    if (useJson) {
      bind[FileIOInterface].to[JsonFileIO]
    } else {
      bind[FileIOInterface].to[XmlFileIO]
    }
  }
}
