package com.grburst.ttorganizer.util

import scala.util.Try
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import android.view.{ View, ViewGroup }
import android.widget.{ TextView, EditText }

package object androidHelper {

  implicit class StringHelper(s: String) {
    def toIntOption: Option[Int] = Try(s.toInt).toOption
  }

  implicit class ViewHelper(vg: ViewGroup) {

    def getChildren: List[View] = {
      ((0 until vg.getChildCount()) map { i =>
        vg.getChildAt(i)
      }).toList
    }

    def getFlatChildren: List[View] = {
      getChildren.flatMap {
        case v: ViewGroup => v.getFlatChildren
        case v: View => Some(v)
      }
    }

    def getTextChildren: List[TextView] = {
      getFlatChildren.collect { case c: TextView => c }
    }

    def getInputTextChildren: List[EditText] = {
      getFlatChildren.collect { case c: EditText => c }
    }
  }

  // implicit class TextViewHelper(t: TextView) {

  // }

}

import macroid._
import macroid.FullDsl._
import macroid.contrib._
import macroid.contrib.Layouts._ // [x]LinearLayout
import macroid.viewable._ //Listable
import macroid.Transformer.Layout

import android.text.InputType
import android.view.ViewGroup.LayoutParams._
import android.widget._
import android.content.Context
import android.content.ContextWrapper

trait Styles {

  def l_weight(weight: Float)(implicit ctx: ContextWrapper): Tweak[View] = lp[LinearLayout](MATCH_PARENT, WRAP_CONTENT, weight)

}

package object tweaks {

  // def l_weight(weight: Float)(implicit ctx: ContextWrapper): Tweak[View] = lp[LinearLayout](MATCH_PARENT, WRAP_CONTENT, weight)
  // val passwordField = Tweak[TextView](f => f.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD))

}

import com.grburst.libtt.types.Player

package object ttHelper extends Styles {

  // def playerListable(implicit ctx: ContextWrapper): Listable[Player, HorizontalLinearLayout] =
  //   Listable[Player].tr {
  //     l[HorizontalLinearLayout](
  //       w[TextView] <~ tweaks.l_weight(0.2f),
  //       w[TextView] <~ l_weight(0.2f),
  //       w[TextView] <~ l_weight(0.2f),
  //       w[TextView] <~ l_weight(0.2f))
  //   }(player => Transformer {
  //     case Layout(w1: TextView, w2: TextView, w3: TextView, w4: TextView) =>
  //       Ui.sequence(
  //         w1 <~ text(s"${player.dRank}"),
  //         w2 <~ text(s"${player.firstname} ${player.surname}"), // <~ On.click(...),
  //         w3 <~ text(s"${player.club}"),
  //         w4 <~ text(s"${player.ttr}"))
  //   })

  // implicit class ListHelper(list: List[Player]) {

  //   def toPlayerListable(implicit ctx: ContextWrapper): Listable[Player, HorizontalLinearLayout] =
  //     Listable[Player].tr {
  //       l[HorizontalLinearLayout](
  //         w[TextView] <~ l_weight(0.2f),
  //         w[TextView] <~ l_weight(0.2f),
  //         w[TextView] <~ l_weight(0.2f),
  //         w[TextView] <~ l_weight(0.2f))
  //     }(player => Transformer {
  //       case Layout(w1: TextView, w2: TextView, w3: TextView, w4: TextView) =>
  //         Ui.sequence(
  //           w1 <~ text(s"${player.dRank}"),
  //           w2 <~ text(s"${player.name}"), // <~ On.click(...),
  //           w3 <~ text(s"${player.club}"),
  //           w4 <~ text(s"${player.ttr}"))
  //     })

  // }

}
