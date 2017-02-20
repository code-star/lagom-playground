package com.example.lagomshopping.impl

import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.persistence.PersistentEntityRegistry
import com.example.lagomshopping.api.OrderService
import java.util.UUID
import scala.concurrent.Future

/**
  * Implementation of the LagomshoppingService.
  */
class OrderServiceImpl(persistentEntityRegistry: PersistentEntityRegistry) extends OrderService {

  override def hello(id: String) = ServiceCall { _ =>
    // Look up the LagomShopping entity for the given ID.
    val ref = persistentEntityRegistry.refFor[OrderEntity](id)

    // Ask the entity the Hello command.
    ref.ask(Hello(id, None))
  }

  override def useGreeting(id: String) = ServiceCall { request =>
    // Look up the LagomShopping entity for the given ID.
    val ref = persistentEntityRegistry.refFor[OrderEntity](id)

    // Tell the entity to use the greeting message specified.
    ref.ask(UseGreetingMessage(request.message))
  }

  override def createOrder() = ServiceCall { _ =>
    val orderId = UUID.randomUUID().toString

    val ref = persistentEntityRegistry.refFor[OrderEntity](orderId)


    ref.ask(CreateOrder(orderId))

    Future.successful(orderId)
  }

  override def addToOrder(orderId: String, productId: String)= ServiceCall { _ =>
    val ref = persistentEntityRegistry.refFor[OrderEntity](orderId)


    ref.ask(AddProductToOrderRequest(productId))
  }

}
