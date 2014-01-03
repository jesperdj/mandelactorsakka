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

object Renderer {
  type RenderFunction = (Double, Double) => Color

  def render(raster: PixelRaster, sampler: Sampler, filter: Filter)(renderFunction: RenderFunction) {
    for ((x, y) <- sampler) {
      val color = renderFunction(x, y)

      // Convert point to raster coordinates
      val rx = x - 0.5
      val ry = y - 0.5

      // Determine the pixels that are to be updated according to the width of the filter
      val minX = math.max((rx - filter.widthX).ceil.toInt, 0)
      val maxX = math.min((rx + filter.widthX).floor.toInt, raster.width - 1)
      val minY = math.max((ry - filter.widthY).ceil.toInt, 0)
      val maxY = math.min((ry + filter.widthY).floor.toInt, raster.height - 1)

      // Update the relevant pixels
      for (py <- minY to maxY; px <- minX to maxX) raster(px, py).add(color, filter(px - rx, py - ry))
    }
  }
}
