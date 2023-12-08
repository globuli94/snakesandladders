val scala3Version = "3.3.1"

lazy val root = project
  .in(file("."))
  .settings(
        name := "snakesandladders",
        version := "0.1.0-SNAPSHOT",
        scalaVersion := scala3Version,
        
        libraryDependencies += ("org.scala-lang.modules" %% "scala-swing" % "3.0.0").cross(CrossVersion.for3Use2_13),
        libraryDependencies += "org.scalameta" %% "munit" % "0.7.29" % Test,
        libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.14",
        libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.14" % "test",
        libraryDependencies += "org.scalafx" %% "scalafx" % "21.0.0-R32",
  )