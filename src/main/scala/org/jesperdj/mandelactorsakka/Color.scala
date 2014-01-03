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

case class Color(red: Double, green: Double, blue: Double) {
  def +(c: Color) = Color(red + c.red, green + c.green, blue + c.blue)

  def *(w: Double) = Color(red * w, green * w, blue * w)

  def /(w: Double) = Color(red / w, green / w, blue / w)

  def toRGB: Int = {
    def clamp(v: Double) = if (v < 0.0) 0.0 else if (v > 1.0) 1.0 else v
    def toByte(v: Double) = math.round(255.0 * clamp(v)).toInt
    toByte(red) << 16 | toByte(green) << 8 | toByte(blue)
  }
}

object Color {
  val Black = Color(0.0, 0.0, 0.0)
}
