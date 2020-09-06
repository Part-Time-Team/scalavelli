name := """client"""
version := s"${(version in ThisBuild).value}"

// enablePlugins(JavaAppPackaging)

// packageName in Universal := s"${name.value}-${version.value}"
// packageSummary in Universal := """Client app for Scalavelli Project"""

// enablePlugins(PackPlugin)

// import xerial.sbt.pack.PackPlugin._
// publishPackArchives
// packSettings
// packMain := Map("client" -> "it.parttimeteam.AppLauncher")

// mainClass in (Compile, packageBin) := Some("it.parttimeteam.AppLauncher")