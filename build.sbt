val scala3Version = "3.3.1"

lazy val root = project
  .in(file("."))
  .enablePlugins(ScoveragePlugin)
  .settings(
        name := "snakesandladders",
        version := "0.1.0-SNAPSHOT",
        scalaVersion := scala3Version,
  
        coverageEnabled := true,
        coverageExcludedPackages := Seq("<your.package.to.exclude>.*"),
        
        libraryDependencies += ("org.scala-lang.modules" %% "scala-swing" % "3.0.0").cross(CrossVersion.for3Use2_13),
        libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.15" % "test",
        libraryDependencies += "net.codingwell" %% "scala-guice" % "7.0.0",
        libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "2.2.0",

        libraryDependencies += "com.typesafe.play" %% "play-json" % "2.10.3",

          coverageEnabled := true

  )
