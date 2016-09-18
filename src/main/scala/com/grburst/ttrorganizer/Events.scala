package com.grburst.ttrorganizer

import org.scaloid.common._

import android.os.Bundle
import android.widget._
import android.view.ViewGroup._
import android.view.ViewGroup.LayoutParams._
import android.view.{Gravity, View}
import android.app.Activity
import android.graphics.Color
import android.text.InputType

import macroid._
import macroid.Ui
import macroid.contrib._
import macroid.contrib.Layouts._
import macroid.viewable._ //Listable
import macroid.FullDsl._
import macroid.Transformer.Layout

// import com.fortysevendeg.macroid.extras._
import com.fortysevendeg.macroid.extras.LinearLayoutTweaks._
import com.fortysevendeg.macroid.extras.ViewTweaks._
import com.fortysevendeg.macroid.extras.TextTweaks._

trait Styles {

  def l_weight(weight: Float): Tweak[View] = lp[LinearLayout](MATCH_PARENT, WRAP_CONTENT, weight)
  // val v_click = Transformer {
  //       case t: TextView => t <~ hide
  //     }

}

class Events extends SActivity with Styles with Contexts[Activity] {

  def ttrEventsListable():Listable[TTREvent, VerticalLinearLayout] =
    Listable[TTREvent].tr{
      l[VerticalLinearLayout](
        w[TextView],
        l[HorizontalLinearLayout](
          w[TextView] <~ l_weight(0.19f),
          w[TextView] <~ l_weight(0.21f),
          w[TextView] <~ l_weight(0.2f),
          w[TextView] <~ l_weight(0.2f),
          w[TextView] <~ l_weight(0.2f)
        )
      )
    } (event => Transformer {
      case Layout(w1: TextView, Layout(w2: TextView, w3: TextView, w4: TextView, w5: TextView, w6: TextView)) =>
          Ui.sequence(
            w1 <~ text(s"${event.name}"),// <~ On.click(w1 <~ v),
            w2 <~ text(s"${event.lDate}"),
            w3 <~ text(s"${event.bilanz}"),
            w4 <~ text(s"${event.ttr}"),
            w5 <~ text(s"${event.ttrDiff}") <~ TextTweaks.color(if(event.ttrDiff < 0) Color.RED else Color.GREEN),
            w6 <~ text(s"${event.ttr + event.ttrDiff}")
          )
    })


  override def onCreate(savedInstanceState: Bundle) = {
    super.onCreate(savedInstanceState)

    var ts = slot[TextView]
    val eventParser = EventsParser()

    setContentView {
        (l[VerticalLinearLayout](
          w[TextView] <~ wire(ts) <~ Some(text("MyTischtennis.de event parser")),
          w[TextView] <~ text(eventParser.currTTR) <~ tvGravity(Gravity.CENTER),
          w[TextView] <~ text("Begegnung"),
          l[HorizontalLinearLayout](
            w[TextView] <~ text("Datum")     <~ l_weight(0.19f),
            w[TextView] <~ text("Bilanz")    <~ l_weight(0.21f),
            w[TextView] <~ text("Alter TTR") <~ l_weight(0.2f),
            w[TextView] <~ text("TTR Diff")  <~ l_weight(0.2f),
            w[TextView] <~ text("Neuer TTR") <~ l_weight(0.2f)
          ),
        w[TextView] <~ lp[LinearLayout](MATCH_PARENT, 1 dp) <~ BgTweaks.color(Color.WHITE),
        w[ListView] <~ ttrEventsListable.listAdapterTweak(eventParser.events())
        ) <~ padding(left = 4 dp, right = 4 dp)
      ).get
    }
  }

}
