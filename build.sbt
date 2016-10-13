name := "TT-Organizer"

import android.Keys._
android.Plugin.androidBuild

javacOptions ++= Seq("-source", "1.7", "-target", "1.7")
scalaVersion := "2.11.8"
scalacOptions in Compile ++= ("-target:jvm-1.7" ::
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

// ProguardKeys.proguardVersion in Proguard := "5.3"
updateCheck in Android := {} // disable update check
proguardCache in Android ++= Seq("org.scaloid")
packagingOptions in Android := PackagingOptions(
  excludes = Seq(
    "META-INF/LICENSE.txt",
    "META-INF/NOTICE.txt",
    "META-INF/LICENSE",
    "META-INF/NOTICE",
    "META-INF/DEPENDENCIES",
    "rootdoc.txt"),
  merges = Seq("reference.conf")) //,
// "reference.conf"))
dexMulti := true

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

proguardOptions in Android ++= Seq("@project/proguard.cfg")
// Proguard keep rules
proguardOptions in Android ++= Seq(
  "-keep class scala.Dynamic",
  "-keep class akka.actor.Actor$class { *; }",
  "-keep class akka.actor.ExtendedActorSystem { *; }",
  "-keep class akka.actor.LightArrayRevolverScheduler { *; }",
  "-keep class akka.actor.LocalActorRefProvider { *; }",
  "-keep class akka.actor.CreatorFunctionConsumer { *; }",
  "-keep class akka.actor.TypedCreatorFunctionConsumer { *; }",
  "-keep class akka.dispatch.BoundedDequeBasedMessageQueueSemantics { *; }",
  "-keep class akka.dispatch.UnboundedMessageQueueSemantics { *; }",
  "-keep class akka.dispatch.UnboundedDequeBasedMessageQueueSemantics { *; }",
  "-keep class akka.dispatch.DequeBasedMessageQueueSemantics { *; }",
  "-keep class akka.dispatch.MultipleConsumerSemantics { *; }",
  "-keep class akka.actor.LocalActorRefProvider$Guardian { *; }",
  "-keep class akka.actor.LocalActorRefProvider$SystemGuardian { *; }",
  "-keep class akka.dispatch.UnboundedMailbox { *; }",
  "-keep class akka.actor.DefaultSupervisorStrategy { *; }",
  "-keep class macroid.akka.AkkaAndroidLogger { *; }",
  "-keep class akka.event.Logging$LogExt { *; }",
  "-keep class spray.client.pipelining.**")

libraryDependencies ++= Seq(
  "com.grburst" %% "libtt" % "0.3.3c-SNAPSHOT",
  "com.grburst" %% "spraytest" % "0.0.3-SNAPSHOT",
  "com.typesafe.akka" %% "akka-actor" % "2.3.9",
  "org.scaloid" %% "scaloid" % "4.2",
  aar("org.macroid" %% "macroid" % "2.0.0-M5"),
  aar("org.macroid" %% "macroid-viewable" % "2.0.0-M5"),
  aar("com.fortysevendeg" %% "macroid-extras" % "0.3"))
dependencyOverrides ++= Set("com.typesafe" % "config" % "1.2.1")

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
