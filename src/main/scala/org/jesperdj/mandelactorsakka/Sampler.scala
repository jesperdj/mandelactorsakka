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

trait Sampler extends Iterable[(Double, Double)]

class StratifiedSampler(rectangle: Rectangle, oversampling: Int = 1) extends Sampler {
  def iterator: Iterator[(Double, Double)] = new Iterator[(Double, Double)] {
    private var px = rectangle.left
    private var py = rectangle.top
    private var sx = 0
    private var sy = 0

    def hasNext: Boolean = py <= rectangle.bottom

    def next(): (Double, Double) = {
      val sample = (px + (sx + 0.5) / oversampling, py + (sy + 0.5) / oversampling)

      sx += 1
      if (sx >= oversampling) {
        sx = 0; sy += 1
        if (sy >= oversampling) {
          sy = 0; px += 1
          if (px > rectangle.right) {
            px = rectangle.left; py += 1
          }
        }
      }

      sample
    }
  }
}
