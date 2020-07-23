name := "scalavelli"

version := "0.1"

scalaVersion in ThisBuild := "2.13.3"
organization in ThisBuild := "it.parttimeteam"

// Determine OS version of JavaFX binaries
lazy val osName = System.getProperty("os.name") match {
  case n if n.startsWith("Linux") => "linux"
  case n if n.startsWith("Mac") => "mac"
  case n if n.startsWith("Windows") => "win"
  case _ => throw new Exception("Unknown platform!")
}

/*
 * START LIBRARY DEFINITIONS.
 */

// TESTS.
// val scalatestV = "3.2.0"
// val scalatest = "org.scalatest" %% "scalatest" % scalatestV % "test"
val scalamock= "org.scalamock" %% "scalamock" % "4.4.0" % Test
val scalatest = "org.scalatest" %% "scalatest" % "3.1.0" % Test
val scalacheck = "org.scalacheck" %% "scalacheck" % "1.14.0" % Test

// val scalactic = "org.scalactic" %% "scalactic" % scalatestV
val scalafx = "org.scalafx" %% "scalafx" % "14-R19"

// AKKA ACTORS.
val akkaV = "2.6.4"
val akkaTyped = "com.typesafe.akka"  %% "akka-actor-typed" % akkaV
val akkaTest = "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaV
val akkaRemote = "com.typesafe.akka" %% "akka-remote" % akkaV

lazy val akkaDependencies = Seq(
  akkaTyped,
  akkaRemote,
  akkaTest % "test"
)

lazy val testDependencies = Seq(
  scalatest,
  scalamock,
  scalacheck
)

// Add JavaFX dependencies
lazy val javaFXModules = Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
lazy val scalaFXDep = javaFXModules.map( m=>
  "org.openjfx" % s"javafx-$m" % "14.0.1" classifier osName
)

/*
 * END LIBRARY DEFINITIONS.
 */

/*
 * START PROJECT SETTINGS.
 */
lazy val compilerOptions = Seq(
  "-deprecation",
  "-encoding",
  "utf8"
)

lazy val commonSettings = Seq(
  scalacOptions ++= compilerOptions,
  resolvers ++= Seq(
    Resolver.sonatypeRepo("snapshots")
  )
)

lazy val settings = commonSettings

/*
 * END PROJECT SETTINGS.
 */

/*
 * START PROJECT DEFINITIONS.
 */

// MODEL PROJECT.
lazy val core = (project in file("core")).settings(
  name := "core",
  settings,
  libraryDependencies ++= testDependencies
)

lazy val commons = (project in file("commons")).settings(
  name := "commons",
  settings,
  libraryDependencies ++= (
    testDependencies :+ akkaTyped
    )
).dependsOn(
  core
)

lazy val server = (project in file("server")).settings(
  name := "server",
  settings,
  libraryDependencies ++= (
    akkaDependencies ++
    testDependencies
    ),
  exportJars := true
).dependsOn(
  core,
  commons
)

lazy val client = (project in file("client")).settings(
  name := "client",
  settings,
  libraryDependencies ++= (akkaDependencies ++
    testDependencies ++
    scalaFXDep.union(Seq(scalafx))
    ),
  exportJars := true
).dependsOn(
  core,
  commons
)

/*
 * END PROJECT DEFINITIONS.
 */