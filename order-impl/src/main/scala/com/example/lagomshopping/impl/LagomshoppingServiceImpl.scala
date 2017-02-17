package com.example.lagomshopping.impl

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.persistence.PersistentEntityRegistry
import com.example.lagomshopping.api.LagomshoppingService

import scala.concurrent.Future

/**
  * Implementation of the LagomshoppingService.
  */
class LagomshoppingServiceImpl(persistentEntityRegistry: PersistentEntityRegistry) extends LagomshoppingService {

  override def hello(id: String) = ServiceCall { _ =>
    // Look up the LagomShopping entity for the given ID.
    val ref = persistentEntityRegistry.refFor[LagomshoppingEntity](id)

    // Ask the entity the Hello command.
    ref.ask(Hello(id, None))
  }

  override def useGreeting(id: String) = ServiceCall { request =>
    // Look up the LagomShopping entity for the given ID.
    val ref = persistentEntityRegistry.refFor[LagomshoppingEntity](id)

    // Tell the entity to use the greeting message specified.
    ref.ask(UseGreetingMessage(request.message))
  }

  override   def addToOrder(customerId:String,productId: String)= ServiceCall{ _ =>
    Future.successful(NotUsed)
  }

}
