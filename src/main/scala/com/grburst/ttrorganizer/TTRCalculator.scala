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

case class SamplePlayer(ttr:Int,
  vRang:Int,
  dRang:Int,
  name:String,
  verein:String,
  ak:Int = 16)


class TTRCalculator extends SActivity with Styles with Contexts[Activity] {

  val player = SamplePlayer(1800, 1, 1, "Blub", "TTV Test", 16)
  // age < 16 => ak + 8
  // age < 21 => ak + 4
  // < 30 graded matches => ak + 4
  // no graded match in 365 days => +4 for 15 matches
  // Nachwuchs-Ausgleich = 0 => newTTR + 2

  def prob(playerA: Double, playerB: Double): Double = 1.0 / (1.0 + Math.pow(10, (playerB - playerA) / 150.0))

  def calcNewTTR(ownTTR:Double, oppTTR: List[Double], victories:Int, ak:Int, na:Int):Int = (ownTTR + Math.round((victories - (oppTTR.map(o => prob(ownTTR, o))).sum) * ak) + na).toInt

  def getTTR(mView: Option[macroid.contrib.Layouts.VerticalLinearLayout]): String = {

    def getTTRValues(view: ViewGroup): List[Double] = {
      ((0 until view.getChildCount()) flatMap { i =>
        view.getChildAt(i) match {
          case v: ViewGroup => getTTRValues(v)
          case v: EditText => Try(v.getText.toString.toDouble).toOption
          case _ => Nil
        }
      }).toList
    }

    def getVictories(view: ViewGroup): List[Int] = {
      ((0 until view.getChildCount()) flatMap { i =>
        view.getChildAt(i) match {
          case v: ViewGroup => getVictories(v)
          case v: CheckBox  => if(v.isChecked) List(1) else Nil
          case _ => Nil
        }
      }).toList
    }

    val ttrVals = getTTRValues(mView.get)
    if(!ttrVals.drop(1).isEmpty) calcNewTTR(ttrVals.head, ttrVals.drop(1), getVictories(mView.get).sum, 16, 0).toString
    else player.ttr.toString
  }

  def addOpponent = {
    val oppField = l[HorizontalLinearLayout](
      w[TextView] <~ text("Gegner: ") <~ l_weight(0.3f),
      w[EditText] <~ numeric <~ l_weight(0.3f),
      w[CheckBox] <~ l_weight(0.3f)
    ) <~ fadeIn(500)
    addViews(Seq(oppField))
  }

  override def onCreate(savedInstanceState: Bundle) = {
    super.onCreate(savedInstanceState)

    var oppFields = slot[VerticalLinearLayout]
    var advOpt = slot[VerticalLinearLayout]
    var wRes = slot[TextView]

    setContentView {
      getUi {
        l[VerticalLinearLayout](
          w[TextView] <~ text("TTR Rechner"),
          l[VerticalLinearLayout](
            l[HorizontalLinearLayout](
              w[TextView] <~ text("Eigener TTR: ") <~ l_weight(0.3f),
              w[EditText] <~ numeric <~ text(player.ttr.toString) <~ l_weight(0.3f),
              w[TextView] <~ text("Sieg?") <~ l_weight(0.3f)
            )
          ) <~ wire(oppFields),
          l[VerticalLinearLayout](
            w[CheckBox] <~ text("Unter 16 Jahre"),
            w[CheckBox] <~ text("Unter 21 Jahre"),
            w[CheckBox] <~ text("Weniger als 30 gewertete Spiele"),
            w[CheckBox] <~ text("Seid 365 Tagen kein gewertetes Spiel"),
            w[CheckBox] <~ text("Nachwuchs-Ausgleich")
          ) <~ wire(advOpt) <~ hide,
          l[HorizontalLinearLayout](
            w[Button] <~ text("Berechne!") <~ On.click(wRes <~ text(getTTR(oppFields))),
            w[Button] <~ text("Weitere Optionen") <~ On.click(advOpt <~ show),
            w[Button] <~ text("+ Gegner") <~ On.click(oppFields <~ addOpponent)
          ),
          w[TextView] <~ text("Neuer TTR") <~ size(25) <~ matchWidth <~ Tweak[TextView](_.setGravity(Gravity.CENTER_HORIZONTAL)),
          w[TextView] <~ wire(wRes) <~ size(50) <~ matchWidth <~ Tweak[TextView](_.setGravity(Gravity.CENTER_HORIZONTAL))
        ) <~ padding(left = 4 dp, right = 4 dp)
      }
    }
  }

}
