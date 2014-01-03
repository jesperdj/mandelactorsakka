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

  val center = Complex(-1.15198, 0.21190)
  val scale = 0.00120
  val maxIterations = 4000

  val palette = new Palette(
    PalettePoint(0.0, Color(0.0, 0.0, 0.6)),
    PalettePoint(0.02, Color(1.0, 0.5, 0.0)),
    PalettePoint(0.08, Color(0.7, 0.1, 0.5)),
    PalettePoint(0.15, Color(0.5, 0.5, 0.7)),
    PalettePoint(0.4, Color(0.2, 0.2, 1.0)),
    PalettePoint(1.0, Color(1.0, 1.0, 1.0)))

  val samplerFactory = { rect: Rectangle => new StratifiedSampler(rect, 2) }
  val filter = new MitchellFilter

  val mandelbrot = new Mandelbrot(Rectangle(0, 0, width - 1, height - 1), center, scale, maxIterations, palette)

  ActorSystem("MandelActorsAkka").actorOf(MainActor.props(width, height, samplerFactory, filter, mandelbrot))
}
