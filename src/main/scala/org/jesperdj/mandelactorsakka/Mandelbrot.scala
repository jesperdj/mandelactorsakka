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

case class Complex(re: Double, im: Double) {
  def +(c: Complex) = Complex(re + c.re, im + c.im)

  def *(c: Complex) = new Complex(re * c.re - im * c.im, im * c.re + re * c.im)

  def modulusSquared = re * re + im * im

  def modulus = math.sqrt(modulusSquared)
}

object Complex {
  val Zero = Complex(0.0, 0.0)
}

class Mandelbrot(rectangle: Rectangle, center: Complex, scale: Double, maxIterations: Int, palette: Double => Color) extends ((Double, Double) => Color) {
  private val lg2 = math.log(2.0)
  private def log2(value: Double) = math.log(value) / lg2

  private val rectWidth = rectangle.width.toDouble
  private val rectHeight = rectangle.height.toDouble

  private val (minC, maxC) = {
    val ratio = rectWidth / rectHeight
    (Complex(center.re - scale, center.im - scale / ratio), Complex(center.re + scale, center.im + scale / ratio))
  }

  private val planeWidth = maxC.re - minC.re
  private val planeHeight = maxC.im - minC.im

  def apply(x: Double, y: Double): Color = {
    // Map raster coordinates to imaginary plane coordinates
    val c = Complex(minC.re + (planeWidth * (x - rectangle.left) / rectWidth), maxC.im - (planeHeight * (y - rectangle.top) / rectHeight))

    var z = Complex.Zero
    var i = 0

    while (z.modulusSquared <= 4.0 && i < maxIterations) {
      z = z * z + c
      i += 1
    }

    // Use normalized iteration count for smooth coloring
    if (i < maxIterations) palette((i - log2(log2(z.modulus))) / maxIterations) else Color.Black
  }
}
