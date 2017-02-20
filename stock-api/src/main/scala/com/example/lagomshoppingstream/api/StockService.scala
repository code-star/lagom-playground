package com.example.lagomshoppingstream.api

import akka.NotUsed
import akka.stream.scaladsl.Source
import com.lightbend.lagom.scaladsl.api.{Service, ServiceCall}

trait StockService extends Service {

  def getStock(productId: String): ServiceCall[NotUsed, Int]

  override final def descriptor = {
    import Service._

    named("stock").withCalls(
      pathCall("/api/stock/:productId", getStock _)
    ).withAutoAcl(true)
  }
}

