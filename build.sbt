name := "MandelActorsAkka"

version := "1.1.0"

scalaVersion := "2.11.2"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies +=
  "com.typesafe.akka" %% "akka-actor" % "2.3.5"

libraryDependencies +=
  "org.scalatest" %% "scalatest" % "2.2.1" % "test"
