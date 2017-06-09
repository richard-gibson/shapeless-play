// *****************************************************************************
// Projects
// *****************************************************************************

lazy val `shapeless-play` =
  project
    .in(file("."))
    .enablePlugins(AutomateHeaderPlugin, GitVersioning)
    .settings(settings)
    .settings(
      libraryDependencies ++= Seq(
        library.shapeless withSources(),
        library.cats withSources(),
        library.jawnP withSources(),
        library.jawnAST withSources(),
        library.scalaCheck % Test,
        library.scalaTest  % Test
      )
    )

// *****************************************************************************
// Library dependencies
// *****************************************************************************

lazy val library =
  new {
    object Version {
      val shapeless   = "2.3.2"
      val cats        = "0.8.1"
      val scalaCheck  = "1.13.4"
      val scalaTest   = "3.0.1"
      val jawn        = "0.10.4"
    }
    val scalaCheck  = "org.scalacheck" %% "scalacheck" % Version.scalaCheck
    val scalaTest   = "org.scalatest"  %% "scalatest"  % Version.scalaTest
    val shapeless   = "com.chuusai" %% "shapeless" % Version.shapeless
    val jawnP       = "org.spire-math" %% "jawn-parser" % Version.jawn
    val jawnAST     = "org.spire-math" %% "jawn-ast" % Version.jawn
    val cats        = "org.typelevel"  %% "cats" % Version.cats
}

// *****************************************************************************
// Settings
// *****************************************************************************

lazy val settings =
  commonSettings ++
  scalafmtSettings ++
  gitSettings ++
  headerSettings

lazy val commonSettings =
  Seq(
    scalaVersion := "2.12.1",
    crossScalaVersions := Seq(scalaVersion.value, "2.11.8"),
    organization := "uk.co.brightcog",
    scalacOptions ++= Seq(
      "-unchecked",
      "-deprecation",
      "-language:_",
      "-target:jvm-1.8",
      "-encoding", "UTF-8"
    ),
    javacOptions ++= Seq(
      "-source", "1.8",
      "-target", "1.8"
    ),
    unmanagedSourceDirectories.in(Compile) :=
      Seq(scalaSource.in(Compile).value),
    unmanagedSourceDirectories.in(Test) :=
      Seq(scalaSource.in(Test).value)
)

lazy val scalafmtSettings =
  reformatOnCompileSettings ++
  Seq(
    formatSbtFiles := false,
    scalafmtConfig :=
      Some(baseDirectory.in(ThisBuild).value / ".scalafmt.conf"),
    ivyScala :=
      ivyScala.value.map(_.copy(overrideScalaVersion = sbtPlugin.value)) // TODO Remove once this workaround no longer needed (https://github.com/sbt/sbt/issues/2786)!
  )

lazy val gitSettings =
  Seq(
    git.useGitDescribe := true
  )

import de.heikoseeberger.sbtheader.HeaderPattern
import de.heikoseeberger.sbtheader.license.Apache2_0
lazy val headerSettings =
  Seq(
    headers := Map(
      "scala" -> (HeaderPattern.cStyleBlockComment,
                  """|/*
                     | * Copyright year author
                     | */""".stripMargin)
    )
  )
