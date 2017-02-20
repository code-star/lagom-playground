package controllers

import com.example.lagomshopping.api.OrderService
import com.example.lagomshoppingstream.api.StockService
import play.api.libs.json.Json
import play.api.mvc.{ Action, Controller }
import scala.concurrent.ExecutionContext

class OrderController(
  orderService: OrderService,
  stockService: StockService
)(implicit executionContext: ExecutionContext) extends Controller {

  def createOrder = Action.async {
    for {
      orderId <- orderService.createOrder.invoke()
    } yield Ok(Json.obj("orderId" -> orderId))
  }

  def addProductToOrder(orderId: String, productId: String) = Action.async {
    for {
      totalOnStock <- stockService.getStock(productId).invoke()
      if totalOnStock > 0
      _ <- orderService.addToOrder(orderId, productId).invoke()
    } yield NoContent
  }
}
