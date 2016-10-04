name := "TT-Organizer"

import android.Keys._
android.Plugin.androidBuild

javacOptions ++= Seq("-source", "1.8", "-target", "1.8")
scalaVersion := "2.11.8"
scalacOptions in Compile ++= (
  "-encoding" :: "UTF-8" ::
  "-unchecked" ::
  "-deprecation" ::
  "-explaintypes" ::
  "-feature" ::
  "-language:_" ::
  "-Xlint:_" ::
  "-Ywarn-unused" ::
  // "-Xdisable-assertions" ::
  // "-optimize" ::
  // "-Yopt:_" :: // enables all 2.12 optimizations
  // "-Yinline" :: "-Yinline-warnings" ::
  Nil)

updateCheck in Android := {} // disable update check
proguardCache in Android ++= Seq("org.scaloid")
packagingOptions in Android := PackagingOptions(Seq("META-INF/LICENSE.txt", "META-INF/NOTICE.txt", "META-INF/LICENSE", "META-INF/NOTICE", "META-INF/DEPENDENCIES", "rootdoc.txt"))

proguardOptions in Android ++= Seq(
  "-ignorewarnings",
  "-dontobfuscate",
  "-dontoptimize",
  "-keepattributes Signature",
  "-printseeds target/seeds.txt",
  "-printusage target/usage.txt",
  "-dontwarn scala.collection.**", // required from Scala 2.11.4
  "-dontwarn org.scaloid.**" // this can be omitted if current Android Build target is android-16
)

libraryDependencies ++= Seq(
  "com.grburst" %% "libtt" % "0.3.0-SNAPSHOT",
  "com.typesafe.akka" %% "akka-actor" % "2.4.11",
  "org.scaloid" %% "scaloid" % "4.2",
  aar("org.macroid" %% "macroid" % "2.0.0-M5"),
  aar("com.fortysevendeg" %% "macroid-extras" % "0.3"),
  aar("org.macroid" %% "macroid-viewable" % "2.0.0-M5"))

run <<= run in Android
install <<= install in Android

// Tests //////////////////////////////

// libraryDependencies ++= Seq(
//   "org.apache.maven" % "maven-ant-tasks" % "2.1.3" % "test",
//   "org.robolectric" % "robolectric" % "3.1.1" % "test",
//   "junit" % "junit" % "4.12" % "test",
//   "com.novocode" % "junit-interface" % "0.11" % "test")

// // without this, @Config throws an exception,
// unmanagedClasspath in Test ++= (bootClasspath in Android).value
