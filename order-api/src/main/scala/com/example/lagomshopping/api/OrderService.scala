package com.example.lagomshopping.api

import akka.{Done, NotUsed}
import com.lightbend.lagom.scaladsl.api.{Service, ServiceCall}
import play.api.libs.json.{Format, Json}

trait OrderService extends Service {

  def hello(id: String): ServiceCall[NotUsed, String]

  def createOrder(): ServiceCall[NotUsed, String]

  def addToOrder(orderId: String, productId: String): ServiceCall[NotUsed, Done]

  def useGreeting(id: String): ServiceCall[GreetingMessage, Done]

  override final def descriptor = {
    import Service._

    named("order").withCalls(
      pathCall("/api/hello/:id", hello _),
      pathCall("/api/hello/:id", useGreeting _),
      pathCall("/api/order", createOrder),
      pathCall("/api/order/:orderId/add/:productId", addToOrder _)
    ).withAutoAcl(true)
  }
}

/**
  * The greeting message class.
  */
case class GreetingMessage(message: String)

object GreetingMessage {
  /**
    * Format for converting greeting messages to and from JSON.
    *
    * This will be picked up by a Lagom implicit conversion from Play's JSON format to Lagom's message serializer.
    */
  implicit val format: Format[GreetingMessage] = Json.format[GreetingMessage]
}
