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

class MapRasterToComplex(width: Int, height: Int, center: Complex, scale: Double) extends ((Double, Double) => Complex) {
  private val rasterWidth = width.toDouble
  private val rasterHeight = height.toDouble

  private val (minC, maxC) = {
    val ratio = rasterWidth / rasterHeight
    (Complex(center.re - scale, center.im - scale / ratio), Complex(center.re + scale, center.im + scale / ratio))
  }

  private val widthC = maxC.re - minC.re
  private val heightC = maxC.im - minC.im

  def apply(x: Double, y: Double): Complex =
    Complex(minC.re + (widthC * x / rasterWidth), maxC.im - (heightC * y / rasterHeight))
}
