package com.example.lagomshoppingstream.impl

import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.server._
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import play.api.libs.ws.ahc.AhcWSComponents
import com.example.lagomshoppingstream.api.StockService
import com.lightbend.lagom.scaladsl.persistence.cassandra.CassandraPersistenceComponents
import com.softwaremill.macwire._

class StockLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new StockApplication(context) {
      override def serviceLocator = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new StockApplication(context) with LagomDevModeComponents

  override def describeServices = List(
    readDescriptor[StockService]
  )
}

abstract class StockApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
    with CassandraPersistenceComponents
    with AhcWSComponents {

  // Bind the services that this server provides
  override lazy val lagomServer = LagomServer.forServices(
    bindService[StockService].to(wire[StockServiceImpl])
  )

  // Register the JSON serializer registry
  override lazy val jsonSerializerRegistry = StockSerializerRegistry

  // Bind the LagomshoppingService client
  lazy val stockService = serviceClient.implement[StockService]
}
