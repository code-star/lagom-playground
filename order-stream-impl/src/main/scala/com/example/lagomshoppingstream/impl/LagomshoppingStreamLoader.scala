package com.example.lagomshoppingstream.impl

import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.server._
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import play.api.libs.ws.ahc.AhcWSComponents
import com.example.lagomshoppingstream.api.LagomshoppingStreamService
import com.example.lagomshopping.api.LagomshoppingService
import com.softwaremill.macwire._

class LagomshoppingStreamLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new LagomshoppingStreamApplication(context) {
      override def serviceLocator = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new LagomshoppingStreamApplication(context) with LagomDevModeComponents

  override def describeServices = List(
    readDescriptor[LagomshoppingStreamService]
  )
}

abstract class LagomshoppingStreamApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
    with AhcWSComponents {

  // Bind the services that this server provides
  override lazy val lagomServer = LagomServer.forServices(
    bindService[LagomshoppingStreamService].to(wire[LagomshoppingStreamServiceImpl])
  )

  // Bind the LagomshoppingService client
  lazy val lagomshoppingService = serviceClient.implement[LagomshoppingService]
}
