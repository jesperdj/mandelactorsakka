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

class Mandelbrot(maxIterations: Int) extends (Complex => Double) {
  private val lg2 = math.log(2.0)
  private def log2(value: Double) = math.log(value) / lg2

  def apply(c: Complex): Double = {
    var z = Complex.Zero
    var i = 0

    while (z.modulusSquared <= 4.0 && i < maxIterations) {
      z = z * z + c
      i += 1
    }

    // Use normalized iteration count for smooth coloring
    if (i < maxIterations) (i - log2(log2(z.modulus))) / maxIterations else Double.PositiveInfinity
  }
}
