/*
 * Copyright year author
 */
package brightcog.shapeless.json

import brightcog.shapeless.json.Encoders._
import brightcog.shapeless.json.JsonOps._
import genJsonImplicits._
import primJsonImplicits._
import cats.syntax.show._
import jawn.ast.JParser
import shapeless.LabelledGeneric

/**
  * Created by richardgibson on 31/12/2016.
  */
object JsonApp extends App {

  case class Foo(ids: List[Int])

  case class Bar(id: Double, desc: String, foo: Foo, isSmth: Option[Boolean])

  val bar: Bar = Bar(1.0, "desc here", Foo(List(1, 2, 3, 3, 3, 2)), Some(true))

  println(Bar(1.0, "desc here", Foo(List(1, 2, 3, 3, 3, 2)), Some(false)).asJson)

  println(bar.asJson.show)
  val gen = LabelledGeneric[Bar]
  println(bar)
  println(gen.to(bar))

  implicit val pf = ParserFacade
  println(JParser.parseFromString(bar.asJson.show))

}
