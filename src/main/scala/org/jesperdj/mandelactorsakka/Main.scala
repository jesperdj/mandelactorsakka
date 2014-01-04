/*
 * Copyright 2014 Jesper de Jong
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jesperdj.mandelactorsakka

import akka.actor.ActorSystem

object Main extends App {
  val width = 1920
  val height = 1080

  val center = Complex(-0.743643, 0.131825)
  val scale = 0.00006
  val maxIterations = 10000

  val palette = new Palette(
    PalettePoint(0.000, Color(0.0, 0.0, 0.4)),
    PalettePoint(0.010, Color(0.1, 0.1, 0.1)),
    PalettePoint(0.018, Color(1.0, 1.0, 0.3)),
    PalettePoint(0.022, Color(0.0, 0.4, 0.0)),
    PalettePoint(0.040, Color(1.0, 1.0, 1.0)),
    PalettePoint(0.200, Color(0.0, 0.0, 0.6)),
    PalettePoint(0.500, Color(0.0, 0.0, 0.0)),
    PalettePoint(1.000, Color(1.0, 1.0, 1.0))
  )

  val samplerFactory = { rect: Rectangle => new StratifiedSampler(rect, 2) }
  val filter = new MitchellFilter

  val mandelbrot = new Mandelbrot(Rectangle(0, 0, width - 1, height - 1), center, scale, maxIterations, palette)

  ActorSystem("MandelActorsAkka").actorOf(MainActor.props(width, height, samplerFactory, filter, mandelbrot), "main")
}
