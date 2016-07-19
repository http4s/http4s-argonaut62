package org.http4s
package argonaut

import java.nio.charset.StandardCharsets

import _root_.argonaut._
import org.http4s.MediaType._
import org.http4s.Status._
import org.http4s.headers.`Content-Type`
import org.http4s.util.byteVector._
import scalaz.stream.Process
import scalaz.stream.text.utf8Decode
import org.specs2.specification.core.Fragment

class ArgonautSpec extends JawnDecodeSupportSpec[Json] with Argonauts {
  testJsonDecoder(json)

  def writeToString[A](a: A)(implicit W: EntityEncoder[A]): String =
    Process.eval(W.toEntity(a))
      .collect { case EntityEncoder.Entity(body, _ ) => body }
      .flatMap(identity)
      .fold1Monoid
      .pipe(utf8Decode)
      .runLastOr("")
      .run

  case class Foo(bar: Int)
  val foo = Foo(42)
  implicit val FooCodec = CodecJson.derive[Foo]

  "json encoder" should {
    val json = Json("test" -> jString("ArgonautSupport"))

    "have json content type" in {
      jsonEncoder.headers.get(`Content-Type`) must_== Some(`Content-Type`(MediaType.`application/json`, Charset.`UTF-8`))
    }

    "write compact JSON" in {
      writeToString(json) must_== ("""{"test":"ArgonautSupport"}""")
    }
  }

  "jsonEncoderOf" should {
    "have json content type" in {
      jsonEncoderOf[Foo].headers.get(`Content-Type`) must_== Some(`Content-Type`(MediaType.`application/json`, Charset.`UTF-8`))
    }

    "write compact JSON" in {
      writeToString(foo)(jsonEncoderOf[Foo]) must_== ("""{"bar":42}""")
    }
  }

  "json" should {
    "handle the optionality of jNumber" in {
      // TODO Urgh.  We need to make testing these smoother.
      // https://github.com/http4s/http4s/issues/157
      def getBody(body: EntityBody): Array[Byte] = body.runLog.run.reduce(_ ++ _).toArray
      val req = Request().withBody(jNumberOrNull(157)).run
      val body = req.decode { json: Json => Response(Ok).withBody(json.number.flatMap(_.toLong).getOrElse(0L).toString)}.run.body
      new String(getBody(body), StandardCharsets.UTF_8) must_== "157"
    }
  }

  "jsonOf" should {
    "decode JSON from an Argonaut decoder" in {
      val result = jsonOf[Foo].decode(Request().withBody(jObjectFields("bar" -> jNumberOrNull(42))).run, strict = true)
      result.run.run must be_\/-(Foo(42))
    }

    // https://github.com/http4s/http4s/issues/514
    Fragment.foreach(Seq("ärgerlich", """"ärgerlich"""")) { wort =>
      case class Umlaut(wort: String)
      implicit val codec = CodecJson.derive[Umlaut]
      val umlautDecoder = jsonOf[Umlaut]
      s"handle JSON with umlauts: $wort" >> {
        val json = Json("wort" -> jString(wort))
        val result = jsonOf[Umlaut].decode(Request().withBody(json).run, strict = true)
        result.run.run must be_\/-(Umlaut(wort))
      }
    }
  }
}
