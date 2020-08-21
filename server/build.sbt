name := """server"""
version := s"${(version in ThisBuild).value}"

enablePlugins(JavaAppPackaging)

packageName in Universal := s"${name.value}-${version.value}"
packageSummary in Universal := """Server app for Scalavelli Project"""