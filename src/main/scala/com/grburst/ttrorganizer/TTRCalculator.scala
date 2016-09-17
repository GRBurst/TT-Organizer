package com.grburst.ttrorganizer

import scala.math.{ pow, round }
import scala.util.Try

import org.scaloid.common._

import android.app.Activity
import android.graphics.Color
import android.view._
import android.view.Gravity
import android.os.Bundle
import android.widget._

import macroid._
import macroid.Snail
import macroid.contrib.Layouts._
import macroid.contrib.TextTweaks._
import macroid.contrib.LpTweaks._
import macroid.contrib.BgTweaks
import macroid.Transformer
import macroid.Transformer.Layout
import macroid.FullDsl._
import com.fortysevendeg.macroid.extras.LinearLayoutTweaks._

// import scala.collection.mutable
// import scala.collection.mutable.ListBuffer

class TTRCalculator extends SActivity with Contexts[Activity] {

  val ak = 16
  val naf = 0
  val siege = 0
  val ttrdiff = 0
  val oldTTR = 1800

  val victories = 1.0

  // def sum(s:Double*) = s.reduce(_ + _)

  def prob(playerA: Double, playerB: Double): Double = 1 / (1 + Math.pow(10, (playerB - playerA) / 150))

  // def calcNewTTR() = oldTTR + Math.round(victories - (oppValues.map(o => prob(oldTTR, (o.get.getText).toString.toDouble))).sum * ak)

  def calcNewTTR(oppValues: List[Double]) = oldTTR + Math.round(victories - (oppValues.map(o => prob(oldTTR, o))).sum * ak)

  def getTTR(mView: Option[macroid.contrib.Layouts.VerticalLinearLayout]): String = {

    def getValues(view: ViewGroup): List[Double] = {
      ((0 until view.getChildCount()) flatMap { i =>
        view.getChildAt(i) match {
          case v: ViewGroup => getValues(v)
          case v: EditText => Try(v.getText.toString.toDouble).toOption
          case _ => Nil
        }
      }).toList
    }

    calcNewTTR(getValues(mView.get)).toString
  }

  def addOpponent = {
    val oppField = l[HorizontalLinearLayout](w[TextView] <~ text("Gegner: ") <~ wrapContent, w[EditText] <~ numeric <~ matchWidth <~ fadeIn(500))
    addViews(Seq(oppField))
  }

  override def onCreate(savedInstanceState: Bundle) = {
    super.onCreate(savedInstanceState)

    var oppFields = slot[VerticalLinearLayout]
    var wRes = slot[TextView]

    setContentView {
      getUi {
        l[VerticalLinearLayout](
          w[TextView] <~ text("TTR Rechner"),
          l[VerticalLinearLayout](
            l[HorizontalLinearLayout](w[TextView] <~ text("Eigener TTR: ") <~ wrapContent, w[EditText] <~ numeric <~ matchWidth <~ fadeIn(500))) <~ wire(oppFields),
          l[HorizontalLinearLayout](
            w[Button] <~ text("Berechne") <~ On.click(wRes <~ text(getTTR(oppFields))),
            w[Button] <~ text("+") <~ On.click(oppFields <~ addOpponent)),
          l[VerticalLinearLayout](
            w[TextView] <~ text("Neuer TTR") <~ size(25) <~ matchWidth <~ Tweak[TextView](_.setGravity(Gravity.CENTER_HORIZONTAL)),
            w[TextView] <~ wire(wRes) <~ size(50) <~ matchWidth <~ Tweak[TextView](_.setGravity(Gravity.CENTER_HORIZONTAL)))) <~ padding(left = 4 dp, right = 4 dp)
      }
    }
  }

}
