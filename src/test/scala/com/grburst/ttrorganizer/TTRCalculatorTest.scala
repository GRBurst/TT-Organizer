package com.grburst.ttrorganizer

import org.junit.Assert._
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.{Robolectric, RobolectricTestRunner}

@RunWith(classOf[RobolectricTestRunner])
@Config(manifest = "src/main/AndroidManifest.xml")
class TTRCalculatorTest {
  @Test def testCalcNewTTRSimple(): Unit = {
    val activity = Robolectric.setupActivity(classOf[TTRCalculator])
    assertTrue(activity.calcNewTTR(1800.0, List(1800.0), 1, 16, 0) == 1808)
  }
  @Test def testCalcNewTTRComplex(): Unit = {
    val activity = Robolectric.setupActivity(classOf[TTRCalculator])
    assertTrue(activity.calcNewTTR(1801, List(1800,1645,1785,1864,1777,1845), 4, 16, 0) == 1814)
  }
}
