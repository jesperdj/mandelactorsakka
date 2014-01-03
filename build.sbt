name := "MandelActorsAkka"

version := "1.0.0"

scalaVersion := "2.10.3"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies +=
  "com.typesafe.akka" %% "akka-actor" % "2.2.3"

libraryDependencies +=
  "org.scalatest" %% "scalatest" % "2.0" % "test"
