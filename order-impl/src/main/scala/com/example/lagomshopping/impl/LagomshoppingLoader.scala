package com.example.lagomshopping.impl

import com.lightbend.lagom.scaladsl.api.ServiceLocator
import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.persistence.cassandra.CassandraPersistenceComponents
import com.lightbend.lagom.scaladsl.server._
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import play.api.libs.ws.ahc.AhcWSComponents
import com.example.lagomshopping.api.LagomshoppingService
import com.softwaremill.macwire._

class LagomshoppingLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new LagomshoppingApplication(context) {
      override def serviceLocator: ServiceLocator = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new LagomshoppingApplication(context) with LagomDevModeComponents

  override def describeServices = List(
    readDescriptor[LagomshoppingService]
  )
}

abstract class LagomshoppingApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
    with CassandraPersistenceComponents
    with AhcWSComponents {

  // Bind the services that this server provides
  override lazy val lagomServer = LagomServer.forServices(
    bindService[LagomshoppingService].to(wire[LagomshoppingServiceImpl])
  )

  // Register the JSON serializer registry
  override lazy val jsonSerializerRegistry = LagomshoppingSerializerRegistry

  // Register the LagomShopping persistent entity
  persistentEntityRegistry.register(wire[LagomshoppingEntity])
}
