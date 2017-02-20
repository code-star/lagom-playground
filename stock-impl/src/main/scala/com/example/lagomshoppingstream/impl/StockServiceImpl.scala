package com.example.lagomshoppingstream.impl

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.example.lagomshoppingstream.api.StockService
import com.lightbend.lagom.scaladsl.persistence.PersistentEntityRegistry
import scala.concurrent.Future

/**
  * Implementation of the LagomshoppingStreamService.
  */
class StockServiceImpl(persistentEntityRegistry: PersistentEntityRegistry) extends StockService {
  override def getStock(productId: String) = ServiceCall { _ =>
    Future.successful(3)
  }
}
