/*
 * Copyright year author
 */
package brightcog.shapeless.json

/**
  * Created by richardgibson on 23/12/2016.
  */
trait Encoder[A] {
  def encode(a: A): JsonValue
}

object Encoder {
  def apply[A](implicit je: Encoder[A]): Encoder[A] = je
  def instance[A](f1: A => JsonValue) = new Encoder[A] {
    override def encode(a: A): JsonValue = f1(a)
  }
}

trait JsonObjEncoder[A] extends Encoder[A] {
  def encode(a: A): JsonObject
}

object JsonObjEncoder {
  def apply[A](implicit je: JsonObjEncoder[A]): JsonObjEncoder[A] = je
  def instance[A](f1: A => JsonObject) =
    new JsonObjEncoder[A] {
      override def encode(a: A): JsonObject = f1(a)
    }
}
