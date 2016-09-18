name := "TTR Organizer"

import android.Keys._
android.Plugin.androidBuild

javacOptions ++= Seq("-source", "1.7", "-target", "1.7")
scalaVersion := "2.11.7"
scalacOptions in Compile += "-feature"

updateCheck in Android := {} // disable update check
proguardCache in Android ++= Seq("org.scaloid")
packagingOptions in Android := PackagingOptions( Seq("META-INF/LICENSE.txt", "META-INF/NOTICE.txt", "META-INF/LICENSE", "META-INF/NOTICE", "META-INF/DEPENDENCIES", "rootdoc.txt") )

proguardOptions in Android ++= Seq("-ignorewarnings -dontobfuscate", "-dontoptimize", "-keepattributes Signature", "-printseeds target/seeds.txt", "-printusage target/usage.txt"
  , "-dontwarn scala.collection.**" // required from Scala 2.11.4
  , "-dontwarn org.scaloid.**" // this can be omitted if current Android Build target is android-16
)

libraryDependencies += "org.scaloid" %% "scaloid" % "4.2"
libraryDependencies += "net.ruippeixotog" %% "scala-scraper" % "1.0.0"
libraryDependencies ++= Seq(
  aar("org.macroid" %% "macroid" % "2.0.0-M5"),
  "com.android.support" % "support-v4" % "20.0.0")
libraryDependencies += aar("com.fortysevendeg" %% "macroid-extras" % "0.3")
libraryDependencies += aar("org.macroid" %% "macroid-viewable" % "2.0.0-M5")

run <<= run in Android
install <<= install in Android

// Tests //////////////////////////////

libraryDependencies ++= Seq(
  "org.apache.maven" % "maven-ant-tasks" % "2.1.3" % "test",
  "org.robolectric" % "robolectric" % "3.0" % "test",
  "junit" % "junit" % "4.12" % "test",
  "com.novocode" % "junit-interface" % "0.11" % "test"
)

// without this, @Config throws an exception,
unmanagedClasspath in Test ++= (bootClasspath in Android).value
