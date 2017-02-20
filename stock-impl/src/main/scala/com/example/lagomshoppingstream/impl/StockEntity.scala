package com.example.lagomshoppingstream.impl

import java.time.LocalDateTime
import akka.{ Done, NotUsed }
import com.lightbend.lagom.scaladsl.persistence.PersistentEntity
import com.lightbend.lagom.scaladsl.persistence.PersistentEntity.ReplyType
import com.lightbend.lagom.scaladsl.playjson.{ JsonSerializer, JsonSerializerRegistry }
import play.api.libs.json.{ Format, Json }
import scala.collection.immutable.Seq

class StockEntity extends PersistentEntity {

  override type Command = StockCommand[_]
  override type Event = StockEvent
  override type State = StockState

  override def initialState: StockState = StockState("Hello", LocalDateTime.now.toString)

  override def behavior: Behavior = {
    case StockState(message, _) => Actions()
  }
}

case class StockState(message: String, timestamp: String)

object StockState {
  implicit val format: Format[StockState] = Json.format
}

sealed trait StockEvent

sealed trait StockCommand[R] extends ReplyType[R]

object StockSerializerRegistry extends JsonSerializerRegistry {
  override def serializers: Seq[JsonSerializer[_]] = Seq()
}
