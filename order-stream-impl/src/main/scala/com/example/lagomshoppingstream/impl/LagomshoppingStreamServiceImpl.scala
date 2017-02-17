package com.example.lagomshoppingstream.impl

import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.example.lagomshoppingstream.api.LagomshoppingStreamService
import com.example.lagomshopping.api.LagomshoppingService

import scala.concurrent.Future

/**
  * Implementation of the LagomshoppingStreamService.
  */
class LagomshoppingStreamServiceImpl(lagomshoppingService: LagomshoppingService) extends LagomshoppingStreamService {
  def stream = ServiceCall { hellos =>
    Future.successful(hellos.mapAsync(8)(lagomshoppingService.hello(_).invoke()))
  }
}
