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
import macroid.IdGeneration
import macroid.Ui
import macroid.contrib._
import macroid.contrib.Layouts._
import macroid.viewable._ //Listable
import macroid.FullDsl.{text => txt, id => mid,_}
import macroid.Transformer.Layout

// import com.fortysevendeg.macroid.extras._
import com.fortysevendeg.macroid.extras.LinearLayoutTweaks._
import com.fortysevendeg.macroid.extras.ViewTweaks._
import com.fortysevendeg.macroid.extras.TextTweaks._

class EventDetail extends SActivity with Styles with Contexts[Activity] {

  def ttrMatchesListable():Listable[TTRMatch, HorizontalLinearLayout] =
    Listable[TTRMatch].tr{
        l[HorizontalLinearLayout](
          w[TextView] <~ l_weight(0.35f),
          w[TextView] <~ l_weight(0.40f),
          w[TextView] <~ l_weight(0.25f)
        )
    } (ttrmatch => Transformer {
      case Layout(w1: TextView, w2: TextView, w3: TextView) =>
          Ui.sequence(
            w1 <~ txt(s"${ttrmatch.opponent} (${ttrmatch.oTTR})"),
            w2 <~ txt(s"${ttrmatch.result} (${ttrmatch.ge})"),
            w3 <~ txt(f"${ttrmatch.set1}%-7s${ttrmatch.set2}%-7s${ttrmatch.set3}%-7s${ttrmatch.set4}%-7s${ttrmatch.set5}%-7s${ttrmatch.set6}%-7s${ttrmatch.set7}%-7s")
          )
    })


  override def onCreate(savedInstanceState: Bundle) = {
    super.onCreate(savedInstanceState)

    val eventDetailParser = EventDetailParser()

    setContentView {
      getUi {
        l[VerticalLinearLayout](
          w[TextView] <~ txt("Spiele / Matches"),
          l[HorizontalLinearLayout](
            w[TextView] <~ txt("Name")      <~ l_weight(0.33f),
            w[TextView] <~ txt("Ergebnis")  <~ l_weight(0.40f),
            w[TextView] <~ txt("Sätze")     <~ l_weight(0.27f)
          ),
        w[TextView] <~ lp[LinearLayout](MATCH_PARENT, 1 dp) <~ BgTweaks.color(Color.WHITE),
        w[ListView] <~ ttrMatchesListable.listAdapterTweak(eventDetailParser.matches())
      ) <~ padding(left = 4 dp, right = 4 dp)
      }
    }

  }

}
