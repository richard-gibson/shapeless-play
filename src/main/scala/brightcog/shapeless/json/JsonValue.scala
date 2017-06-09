/*
 * Copyright year author
 */
package brightcog.shapeless.json

/**
  * Created by GibsonR on 10/12/2016.
  */
sealed trait JsonValue

case class JsonObject(fields: List[(String, JsonValue)]) extends JsonValue
case class JsonArray(items: Seq[JsonValue])              extends JsonValue
case class JsonString(value: String)                     extends JsonValue
case class JsonNumber[A: Numeric](value: A)              extends JsonValue
//case class JsonBoolean(value: Boolean) extends JsonValue
trait JsonBoolean extends JsonValue {
  final def asBoolean: Boolean = this == JsonTrue
}
object JsonBoolean {
  final val True: JsonBoolean              = JsonTrue
  final val False: JsonBoolean             = JsonFalse
  final def apply(b: Boolean): JsonBoolean = if (b) JsonTrue else JsonFalse
  final def unapply(jb: JsonBoolean): Option[Boolean] =
    Option(jb).map(_ == JsonTrue)
}

case object JsonTrue  extends JsonBoolean
case object JsonFalse extends JsonBoolean

case object JsonNull extends JsonValue
object JsonOps {

  implicit class JsonExtras[T](t: T) {
    def asJson(implicit je: Encoder[T]): JsonValue =
      je.encode(t)
  }

}
