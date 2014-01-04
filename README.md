MandelActorsAkka
================

Mandelbrot generator implemented with Akka actors.

This is a version of my MandelActors project, but implemented using [Akka](http://akka.io/) actors instead of Scala actors.

This project uses [`sbt`](http://www.scala-sbt.org/). You should be able to compile and run it by installing `sbt` and running the command `sbt run`.

At the moment, configuration is done directly in the source code, in `Main.scala`.

MandelActorsAkka was created using [Scala](http://scala-lang.org/) 2.10.3, sbt 0.13.1 and Akka 2.2.3.
