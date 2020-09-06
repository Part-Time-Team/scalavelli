name := """server"""
version := s"${(version in ThisBuild).value}"

// enablePlugins(JavaAppPackaging)

// packageName in Universal := s"${name.value}-${version.value}"
// packageSummary in Universal := """Server app for Scalavelli Project"""

// enablePlugins(PackPlugin)

// import xerial.sbt.pack.PackPlugin._
// publishPackArchives
// packSettings
// packMain := Map("client" -> "it.parttimeteam.ScalavelliServer")

// mainClass in (Compile, packageBin) := Some("it.parttimeteam.ScalavelliServer")