/*
 * Copyright year author
 */
package brightcog.shapeless.json

import jawn.SimpleFacade

/**
  * Created by richardgibson on 23/12/2016.
  */
object ParserFacade extends SimpleFacade[JsonValue] {

  def jnull()                             = JsonNull
  def jfalse()                            = JsonFalse
  def jtrue()                             = JsonTrue
  def jnum(s: String)                     = JsonNumber(s.toDouble)
  def jint(s: String)                     = JsonNumber(s.toDouble)
  def jstring(s: String)                  = JsonString(s)
  def jarray(vs: List[JsonValue])         = JsonArray(vs)
  def jobject(vs: Map[String, JsonValue]) = JsonObject(vs.toList)
}
