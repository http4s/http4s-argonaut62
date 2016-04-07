import scala.util.Properties.envOrNone

organization := "org.http4s"
version := s"0.13.1"
name := "http4s-argonaut61"
description := "argonaut-6.1 support for http4s"

scalaVersion := "2.10.6"
crossScalaVersions <<= scalaVersion(Seq(_, "2.11.8"))

libraryDependencies ++= Seq(
  "io.argonaut" %% "argonaut" % "6.1",
  "org.spire-math" %% "jawn-argonaut" % "0.8.4",
  "org.http4s" %% "http4s-jawn" % "0.13.1",
  "org.specs2" %% "specs2-core" % "3.7.2-scalaz-7.1.7"
)

pomExtra := {
  <url>https://github.com/http4s/http4s-argonaut61</url>
  <licenses>
    <license>
      <name>Apache 2</name>
      <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
    </license>
  </licenses>
  <scm>
    <connection>scm:git:github.com/http4s/http4s-argonaut61</connection>
    <developerConnection>scm:git:git@github.com:http4s/http4s-argonaut61</developerConnection>
    <url>https://github.com/http4s/http4s-argonaut61</url>
  </scm>
  <developers>
    <developer>
      <id>rossabaker</id>
      <name>Ross A. Baker</name>
    </developer>
  </developers>
}
