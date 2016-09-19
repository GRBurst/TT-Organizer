package com.grburst.ttrorganizer

import com.grburst.ttrorganizer.androidHelper._

import scala.math.{ pow, round }
import scala.util.Try

import org.scaloid.common._
import scala.collection.mutable

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

//TODO:
//  - Add advanced options to calculation
//  - Implement TextWatcher to automatically update new ttr with 4 numbers are entered
//  - Automatically recalculate if checkbox is changed
//  - Listview for opponents -> currently if #opponenets > 5 new ttr not viewable anymore

class TTRCalculator extends SActivity with Styles with Contexts[Activity] {

  case class CalcField(eSlot: Option[android.widget.EditText], cSlot: Option[android.widget.CheckBox]) {

    var ttrValue = eSlot
    var victoryBox = cSlot

    def getTTRValue: Option[Double] = Try(ttrValue.get.text.toString.toDouble).toOption
    def hasValidTTR: Boolean = getTTRValue.isDefined

    def isVictorious: Option[Boolean] = Try(victoryBox.get.isChecked).toOption
  }

  // age < 16 => ak + 8
  // age < 21 => ak + 4
  // < 30 graded matches => ak + 4
  // no graded match in 365 days => +4 for 15 matches
  // Nachwuchs-Ausgleich = 0 => newTTR + 2

  def prob(playerA: Double, playerB: Double): Double = 1.0 / (1.0 + Math.pow(10, (playerB - playerA) / 150.0))

  def calcNewTTR(ownTTR: Double, oppTTR: List[Double], victories: Int, ak: Int, na: Int): Int = (ownTTR + Math.round((victories - (oppTTR.map(o => prob(ownTTR, o))).sum) * ak) + na).toInt

  def getTTR(fields: mutable.ArrayBuffer[CalcField]): String = {
    val victories: Int = fields.drop(1).count(l => l.hasValidTTR && l.isVictorious.getOrElse(false))
    val ttrVals: List[Double] = fields.drop(1).map(l => l.getTTRValue).flatten.toList
    if (!ttrVals.isEmpty && fields.head.hasValidTTR) calcNewTTR(fields.head.getTTRValue.get, ttrVals, victories, 16, 0).toString
    else if (fields.head.hasValidTTR) fields.head.getTTRValue.get.toInt.toString
    else ""
  }

  def addOpponent(fields: mutable.ArrayBuffer[CalcField]) = {

    fields += CalcField(slot[EditText], slot[CheckBox])
    addViews(Seq(l[HorizontalLinearLayout](
      w[TextView] <~ text("Gegner: ") <~ l_weight(0.3f),
      w[EditText] <~ numeric <~ l_weight(0.3f) <~ wire(fields.last.ttrValue),
      w[CheckBox] <~ l_weight(0.3f) <~ wire(fields.last.victoryBox)) <~ fadeIn(500)))
  }

  override def onCreate(savedInstanceState: Bundle) = {
    super.onCreate(savedInstanceState)

    var oppFields = slot[VerticalLinearLayout]
    var advOpt = slot[VerticalLinearLayout]
    var wRes = slot[TextView]

    val calcFields: mutable.ArrayBuffer[CalcField] = mutable.ArrayBuffer()

    calcFields += CalcField(slot[EditText], None)

    setContentView {
      (l[VerticalLinearLayout](
        w[TextView] <~ text("TTR Rechner"),
        l[VerticalLinearLayout](
          l[HorizontalLinearLayout](
            w[TextView] <~ text("Eigener TTR: ") <~ l_weight(0.3f),
            w[EditText] <~ numeric <~ text("1800") <~ l_weight(0.3f) <~ wire(calcFields.head.ttrValue),
            w[TextView] <~ text("Sieg?") <~ l_weight(0.3f))) <~ wire(oppFields),
        l[VerticalLinearLayout](
          w[CheckBox] <~ text("Unter 16 Jahre"),
          w[CheckBox] <~ text("Unter 21 Jahre"),
          w[CheckBox] <~ text("Weniger als 30 gewertete Spiele"),
          w[CheckBox] <~ text("Seid 365 Tagen kein gewertetes Spiel"),
          w[CheckBox] <~ text("Nachwuchs-Ausgleich")) <~ wire(advOpt) <~ hide,
        l[HorizontalLinearLayout](
          w[Button] <~ text("Berechne!") <~ On.click(wRes <~ text(getTTR(calcFields))),
          w[Button] <~ text("Weitere Optionen") <~ On.click(advOpt <~ show),
          w[Button] <~ text("+ Gegner") <~ On.click(oppFields <~ addOpponent(calcFields))),
        w[TextView] <~ text("Neuer TTR") <~ size(25) <~ matchWidth <~ Tweak[TextView](_.setGravity(Gravity.CENTER_HORIZONTAL)),
        w[TextView] <~ wire(wRes) <~ size(50) <~ matchWidth <~ Tweak[TextView](_.setGravity(Gravity.CENTER_HORIZONTAL))) <~ padding(left = 4 dp, right = 4 dp)).get
    }
  }

}
