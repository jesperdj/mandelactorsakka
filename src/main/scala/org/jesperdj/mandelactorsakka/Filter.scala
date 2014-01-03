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

trait Filter extends ((Double, Double) => Double) {
  val widthX: Double
  val widthY: Double
}

class BoxFilter(val widthX: Double = 0.5, val widthY: Double = 0.5) extends Filter {
  def apply(x: Double, y: Double) = 1.0
}

class TriangleFilter(val widthX: Double = 2.0, val widthY: Double = 2.0) extends Filter {
  def apply(x: Double, y: Double) = math.max(0.0, widthX - x.abs) * math.max(0.0, widthY - y.abs)
}

class GaussianFilter(val widthX: Double = 2.0, val widthY: Double = 2.0, alpha: Double = 2.0) extends Filter {
  private val expX = math.exp(-alpha * widthX * widthX)
  private val expY = math.exp(-alpha * widthY * widthY)

  private def gaussian(d: Double, exp: Double) = math.max(0.0, math.exp(-alpha * d * d) - exp)

  def apply(x: Double, y: Double) = gaussian(x, expX) * gaussian(y, expY)
}

class MitchellFilter(val widthX: Double = 2.0, val widthY: Double = 2.0, b: Double = 1.0 / 3.0, c: Double = 1.0 / 3.0) extends Filter {
  private val (p10, p11, p12, p13) = (1.0 - b / 3.0, 0.0, -3.0 + 2.0 * b + c, 2.0 - 1.5 * b - c)
  private val (p20, p21, p22, p23) = (4.0 / 3.0 * b + 4.0 * c, -2.0 * b - 8.0 * c, b + 5.0 * c, -b / 6.0 - c)

  private def mitchell(v: Double) = {
    val x = 2.0 * v.abs
    if (x <= 1.0) p10 + p11 * x + p12 * x * x + p13 * x * x * x else p20 + p21 * x + p22 * x * x + p23 * x * x * x
  }

  def apply(x: Double, y: Double) = mitchell(x / widthX) * mitchell(y / widthY)
}

class LanczosSincFilter(val widthX: Double = 4.0, val widthY: Double = 4.0, tau: Double = 3.0) extends Filter {
  private val π = math.Pi

  private def lanczosSinc(v: Double) = {
    val x = v.abs
    if (x < 1e-6) 1.0 else if (x > 1.0) 0.0 else {
      val w = π * x
      val wt = w * tau
      (math.sin(wt) / wt) * (math.sin(w) / w)
    }
  }

  def apply(x: Double, y: Double) = lanczosSinc(x / widthX) * lanczosSinc(y / widthY)
}
