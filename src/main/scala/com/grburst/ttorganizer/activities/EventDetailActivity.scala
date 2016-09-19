package com.grburst.ttorganizer.activities

import com.grburst.ttorganizer.parser.EventDetailParser
import com.grburst.ttorganizer.util.TTMatch

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

class EventDetailActivity extends SActivity with Contexts[Activity] with Styles {

  def ttrMatchesListable():Listable[TTMatch, HorizontalLinearLayout] =
    Listable[TTMatch].tr{
        l[HorizontalLinearLayout](
          w[TextView] <~ l_weight(0.33f),
          w[TextView] <~ l_weight(0.40f),
          w[TextView] <~ l_weight(0.27f)
        )
    } (ttrmatch => Transformer {
      case Layout(w1: TextView, w2: TextView, w3: TextView) =>
          Ui.sequence(
            w1 <~ text(s"${ttrmatch.opponent} (${ttrmatch.oTTR})"),
            w2 <~ text(s"${ttrmatch.result} (${ttrmatch.ge})"),
            w3 <~ text(f"${ttrmatch.set1}%-7s${ttrmatch.set2}%-7s${ttrmatch.set3}%-7s${ttrmatch.set4}%-7s${ttrmatch.set5}%-7s${ttrmatch.set6}%-7s${ttrmatch.set7}%-7s")
          )
    })


  override def onCreate(savedInstanceState: Bundle) = {
    super.onCreate(savedInstanceState)

    val eventDetailParser = EventDetailParser()

    setContentView {
        (l[VerticalLinearLayout](
          w[TextView] <~ text("Spiele / Matches"),
          l[HorizontalLinearLayout](
            w[TextView] <~ text("Name")      <~ l_weight(0.33f),
            w[TextView] <~ text("Ergebnis")  <~ l_weight(0.40f),
            w[TextView] <~ text("Sätze")     <~ l_weight(0.27f)
          ),
        w[TextView] <~ lp[LinearLayout](MATCH_PARENT, 1 dp) <~ BgTweaks.color(Color.WHITE),
        w[ListView] <~ ttrMatchesListable.listAdapterTweak(eventDetailParser.get())
      ) <~ padding(left = 4 dp, right = 4 dp)
      ).get
    }

  }

}
