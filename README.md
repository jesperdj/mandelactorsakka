MandelActorsAkka
================

Mandelbrot generator implemented with Akka actors.

This is a version of my MandelActors project, but implemented using [Akka](http://akka.io/) actors instead of Scala actors.

This project uses the [sbt build tool](http://www.scala-sbt.org/). You should be able to compile and run it by installing sbt and running the command `sbt run`. The program might need a lot of memory, especially if the size of the image you are rendering is large. Set the environment variable `SBT_OPTS` before running `sbt run` to give the JVM more memory. For example: `set SBT_OPTS=-Xmx4G`

At the moment, configuration is done directly in the source code, in `Main.scala`.

MandelActorsAkka was created using [Scala](http://scala-lang.org/) 2.10.3, sbt 0.13.1 and Akka 2.2.3.
