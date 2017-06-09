/*
 * Copyright year author
 */
package brightcog.shapeless.json

import cats._
import shapeless._
import shapeless.labelled.FieldType

object Encoders {

  object primJsonImplicits {
    implicit val jsonShow: Show[JsonValue] =
      Show.show[JsonValue](Printer.spaces2.pretty)

    implicit val stringEncoder: Encoder[String] =
      Encoder.instance[String](JsonString.apply)

    implicit val boolEncoder: Encoder[Boolean] =
      Encoder.instance[Boolean](JsonBoolean.apply)

    implicit def numericEncoder[A: Numeric]: Encoder[A] =
      Encoder.instance[A](JsonNumber.apply)

    implicit def listEncoder[A](implicit enc: Encoder[A]): Encoder[List[A]] =
      Encoder.instance[List[A]](l => JsonArray(l.map(enc.encode)))

    //TODO: fix jsonValue
    implicit def optEncoder[A](implicit enc: Encoder[A]): Encoder[Option[A]] =
      Encoder.instance[Option[A]](_.map(enc.encode).getOrElse(JsonNull))

  }

  object genJsonImplicits {
    // Base case for products
    implicit val jsonHNil: JsonObjEncoder[HNil] =
      JsonObjEncoder.instance[HNil](_ => JsonObject(Nil))

    implicit def jsonHCons[K <: Symbol, H, T <: HList](
        implicit witness: Witness.Aux[K],
        jsonEncHd: Lazy[Encoder[H]],
        jsonEncTl: JsonObjEncoder[T]): JsonObjEncoder[FieldType[K, H] :: T] = {
      val fldNme = witness.value.name
      JsonObjEncoder.instance[FieldType[K, H] :: T](
        hlst => {
          val hd             = jsonEncHd.value.encode(hlst.head)
          val tl: JsonObject = jsonEncTl.encode(hlst.tail)
          JsonObject((fldNme, hd) :: tl.fields)
        }
      )
    }

    implicit def genObjImp[A, H <: HList](
        implicit gen: LabelledGeneric.Aux[A, H],
        jsonEnc: Lazy[JsonObjEncoder[H]]): Encoder[A] =
      JsonObjEncoder.instance[A](obj => jsonEnc.value.encode(gen.to(obj)))

  }

}
