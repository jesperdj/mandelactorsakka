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

import akka.actor.{Actor, ActorRef, Props}

object RenderActor {
  case object Render
  case class RenderResult(raster: PixelRaster)

  def props(width: Int, height: Int, samplerFactory: Rectangle => Sampler, filter: Filter, renderFunction: (Double, Double) => Color) =
    Props(new RenderActor(width, height, samplerFactory, filter, renderFunction))
}

class RenderActor(width: Int, height: Int, samplerFactory: Rectangle => Sampler, filter: Filter, renderFunction: (Double, Double) => Color) extends Actor {
  import RenderActor._
  import ComputeActor._

  private val tileSizeThreshold = 4096

  def receive = {
    case Render =>
      val rectangle = Rectangle(0, 0, width - 1, height - 1)
      val tiles = createTiles(rectangle, tileSizeThreshold)
      val raster = new PixelRaster(width, height)
      context.become(rendering(raster, tiles.size, sender))
      tiles foreach { tile => context.actorOf(ComputeActor.props(samplerFactory(tile), renderFunction)) ! Compute }
  }

  def rendering(raster: PixelRaster, numberOfActiveActors: Int, receiver: ActorRef): Receive = {
    case ComputeResult(result) =>
      context.stop(sender)
      result foreach { case (x, y, color) => updateRaster(raster, x, y, color) }
      if (numberOfActiveActors > 1) context.become(rendering(raster, numberOfActiveActors - 1, receiver))
      else receiver ! RenderResult(raster)
  }

  private def createTiles(rectangle: Rectangle, threshold: Int): List[Rectangle] = {
    if (rectangle.width * rectangle.height < threshold) List(rectangle)
    else {
      val splitX = rectangle.left + rectangle.width / 2
      val splitY = rectangle.top + rectangle.height / 2

      createTiles(Rectangle(rectangle.left, rectangle.top, splitX - 1, splitY - 1), threshold) :::
        createTiles(Rectangle(splitX, rectangle.top, rectangle.right, splitY - 1), threshold) :::
        createTiles(Rectangle(rectangle.left, splitY, splitX - 1, rectangle.bottom), threshold) :::
        createTiles(Rectangle(splitX, splitY, rectangle.right, rectangle.bottom), threshold)
    }
  }

  private def updateRaster(raster: PixelRaster, x: Double, y: Double, color: Color) {
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
