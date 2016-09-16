package com.grburst.ttrorganizer

import scala.math.{pow, round}

import org.scaloid.common._

import android.app.Activity
import android.graphics.Color
import android.view.Gravity
import android.os.Bundle
import android.widget._

import macroid._
import macroid.Snail
import macroid.contrib.Layouts._
import macroid.contrib.TextTweaks._
import macroid.contrib.LpTweaks._
import macroid.contrib.BgTweaks
import macroid.FullDsl._
import com.fortysevendeg.macroid.extras.LinearLayoutTweaks._

class TTRCalculator() extends SActivity with Contexts[Activity] {

  val ak = 16
  val naf = 0
  val siege = 0
  val ttrdiff = 0
  val oldTTR = 1800

  def victories = 0

  def sum(a:Double) = a

  def prob(playerA:Int, playerB:Int):Double = 1/(1 + Math.pow(10, (playerB.toDouble - playerA.toDouble)/150))

  def calcNewTTR() = oldTTR + Math.round(victories - sum(prob(1800, 1700)) * ak)

  override def onCreate(savedInstanceState: Bundle) = {
    super.onCreate(savedInstanceState)

    var oppFields = slot[VerticalLinearLayout]
    var wRes = slot[TextView]

    val oppField = l[HorizontalLinearLayout](w[TextView] <~ text("Gegner: ") <~ wrapContent, w[EditText] <~ numeric <~ matchWidth <~ fadeIn(500))

    setContentView {
      getUi {
        l[VerticalLinearLayout](
          w[TextView] <~ text("TTR Rechner"),
          l[VerticalLinearLayout](oppField) <~ wire(oppFields),
          l[HorizontalLinearLayout](
            w[Button] <~ text("Berechne") <~ On.click(wRes <~ text(calcNewTTR().toString)),
            w[Button] <~ text("+") <~ On.click(oppFields <~ addViews(Seq(oppField)))
          ),
          l[VerticalLinearLayout](
            w[TextView] <~ text("Neuer TTR") <~ size(25) <~ matchWidth <~ Tweak[TextView](_.setGravity(Gravity.CENTER_HORIZONTAL)),
            w[TextView] <~ wire(wRes) <~ size(50) <~ matchWidth <~ Tweak[TextView](_.setGravity(Gravity.CENTER_HORIZONTAL))
          )
        ) <~ padding(left = 4 dp, right = 4 dp)
      }
    }
  }

}
  // (1/(1+Math.pow(10,(parseFloat(document.calc.gegnera.value) - parseFloat(document.calc.dttr.value))/150)))*100)
  // document.calc.ttrneu.value = naf + parseFloat(document.calc.dttr.value) + Math.round((siege - parseFloat(document.calc.output1.value)/100 - parseFloat(document.calc.output2.value)/100 - parseFloat(document.calc.output3.value)/100 - parseFloat(document.calc.output4.value)/100 - parseFloat(document.calc.output5.value)/100 - parseFloat(document.calc.output6.value)/100 - parseFloat(document.calc.output7.value)/100 - parseFloat(document.calc.output8.value)/100 - parseFloat(document.calc.output9.value)/100 - parseFloat(document.calc.output10.value)/100)*ak)
  // document.calc.ttrdiff.value = document.calc.ttrneu.value - parseFloat(document.calc.dttr.value)
