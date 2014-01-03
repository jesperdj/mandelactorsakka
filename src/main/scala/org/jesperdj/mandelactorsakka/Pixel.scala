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

class Pixel {
  private var _color: Color = Color.Black
  private var weight: Double = 0.0

  def color: Color = if (weight > 0.0) _color / weight else Color.Black

  def add(c: Color, w: Double) {
    _color += c * w
    weight += w
  }

  def toARGB: Int = if (weight > 0.0) 0xFF000000 | color.toRGB else 0x00000000

  override def toString = s"Pixel(${_color},$weight)"
}

object Pixel {
  def apply() = new Pixel
}
