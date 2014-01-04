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

import akka.actor.{Actor, ActorLogging, Props}
import javax.imageio.ImageIO
import java.io.File

object MainActor {
  def props(width: Int, height: Int, samplerFactory: Rectangle => Sampler, filter: Filter,
            renderFunction: (Double, Double) => Color) =
    Props(new MainActor(width, height, samplerFactory, filter, renderFunction))
}

class MainActor(width: Int, height: Int, samplerFactory: Rectangle => Sampler, filter: Filter,
                renderFunction: (Double, Double) => Color) extends Actor with ActorLogging {
  import RenderActor._

  context.actorOf(RenderActor.props(width, height, samplerFactory, filter, renderFunction), "render") ! Render

  def receive = {
    case RenderResult(raster) =>
      log.info("Saving image")
      ImageIO.write(raster.toImage, "png", new File("C:\\Temp\\mandelbrot.png"))
      context.system.shutdown()
  }
}
