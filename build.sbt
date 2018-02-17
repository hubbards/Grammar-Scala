// sbt build file

// factor out common settings into a sequence
lazy val commonSettings = Seq(
  version := "1.0",
  scalaVersion := "2.11.7"
)

lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    name := "Grammar-Scala",
    scalaSource in Compile := baseDirectory.value / "src"
  )
