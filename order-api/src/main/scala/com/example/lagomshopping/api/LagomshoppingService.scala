package com.example.lagomshopping.api

import akka.{Done, NotUsed}
import com.lightbend.lagom.scaladsl.api.{Service, ServiceCall}
import play.api.libs.json.{Format, Json}

/**
  * The LagomShopping service interface.
  * <p>
  * This describes everything that Lagom needs to know about how to serve and
  * consume the LagomshoppingService.
  */
trait LagomshoppingService extends Service {

  /**
    * Example: curl http://localhost:9000/api/hello/Alice
    */
  def hello(id: String): ServiceCall[NotUsed, String]

  def addToOrder(customerId:String,productId: String): ServiceCall[NotUsed, NotUsed]
  /**
    * Example: curl -H "Content-Type: application/json" -X POST -d '{"message":
    * "Hi"}' http://localhost:9000/api/hello/Alice
    */
  def useGreeting(id: String): ServiceCall[GreetingMessage, Done]

  override final def descriptor = {
    import Service._
    // @formatter:off
    named("lagomshopping").withCalls(
      pathCall("/api/hello/:id", hello _),
      pathCall("/api/hello/:id", useGreeting _),
      pathCall("/api/order/:customerId/add/:productId",addToOrder _)
    ).withAutoAcl(true)
    // @formatter:on
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
