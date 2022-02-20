name := "validation-tagless-final"

version := "0.1"

scalaVersion := "2.13.7"

addCompilerPlugin("org.typelevel" % "kind-projector" % "0.13.2" cross CrossVersion.full)
libraryDependencies += "org.typelevel" %% "cats-core" % "2.0.0"