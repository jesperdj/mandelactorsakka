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

import scala.reflect.ClassTag

class Raster[T : ClassTag](val width: Int, val height: Int)(newElem: => T) {
  private val data = new Array[T](width * height)
  for (i <- 0 until data.length) data(i) = newElem

  private def index(x: Int, y: Int): Int = x + y * width

  def apply(x: Int, y: Int): T = data(index(x, y))

  def update(x: Int, y: Int, value: T): Unit = data(index(x, y)) = value
}
