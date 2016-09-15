package com.grburst.ttrorganizer

import org.scaloid.common._

import android.os.Bundle
// import android.widget.{ListView, LinearLayout, TextView, Button}
// import android.view.ViewGroup.LayoutParams._
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

import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.model.Element
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL.Parse._

case class Event(sDate:String,
  lDate:String,
  name:String,
  id:Int,
  ak:String,
  bilanz:String,
  gewinnerwartung:String,
  typ:String,
  ttr:Int,
  ttrDiff:Int)
  {

  }

trait Styles {

  def l_weight(weight: Float): Tweak[View] = lp[LinearLayout](MATCH_PARENT, WRAP_CONTENT, weight)

  def ttrDataListable(implicit ctx: ActivityContext, appCtx: AppContext):Listable[Event, VerticalLinearLayout] =
    Listable[Event].tr{
      l[VerticalLinearLayout](
        w[TextView],
        l[HorizontalLinearLayout](
          w[TextView] <~ l_weight(0.2f),
          w[TextView] <~ l_weight(0.2f),  //<~ gravity(Gravity.CENTER),
          w[TextView] <~ l_weight(0.2f),
          w[TextView] <~ l_weight(0.2f),
          w[TextView] <~ l_weight(0.2f)
        )
      )
    } (event => Transformer {
      case Layout(w1: TextView, Layout(w2: TextView, w3: TextView, w4: TextView, w5: TextView, w6: TextView)) =>
          Ui.sequence(
            w1 <~ txt(s"${event.name} => ${event.id}"),
            w2 <~ txt(s"${event.lDate}"),
            w3 <~ txt(s"${event.bilanz}"),
            w4 <~ txt(s"${event.ttr}"),
            w5 <~ txt(s"${event.ttrDiff}") <~ TextTweaks.color(if(event.ttrDiff < 0) Color.RED else Color.GREEN),
            w6 <~ txt(s"${event.ttr + event.ttrDiff}")
          )
    })

}

class TTROrganizer extends Activity with Styles with IdGeneration with Contexts[Activity] {

  override def onCreate(savedInstanceState: Bundle) = {
    super.onCreate(savedInstanceState)

    var ts = slot[TextView]

    val browser = JsoupBrowser()
    val eventDoc = browser.parseFile("/storage/emulated/0/mytischtennis.de/events")

    val ttrTable = eventDoc >> element("#tooltip-wrapper-ttr-table") >> element("tbody") >> elementList("tr")
    val ttrData: List[Option[Event]] = ttrTable.map(x => (x >> elementList("td")).toList match {
      case List(s,l,e,a,b,g,ty,tr,td) => {
        val eIdt:String = e >> attr("href")("a")
        val eId = eIdt.substring(eIdt.indexOf("(") + 1, eIdt.indexOf(",",eIdt.indexOf("(")))
        Some(Event(s.text,l.text,e.text,eId.toInt,a.text,b.text,g.text,ty.text,tr.text.toInt,td.text.toInt))
      }
      case _ => None
    })

    setContentView {
      getUi {
        l[VerticalLinearLayout](
          w[TextView] <~ wire(ts) <~ Some(txt("MyTischtennis.de event parser")),
          w[TextView] <~ txt(doc >> element(".ttr-box") >> text("h3")) <~ tvGravity(Gravity.CENTER),
          w[TextView] <~ txt("Begegnung"),
          l[HorizontalLinearLayout](
            w[TextView] <~ txt("Datum") <~ l_weight(0.2f),
            w[TextView] <~ txt("Bilanz") <~ l_weight(0.2f),
            w[TextView] <~ txt("Alter TTR") <~ l_weight(0.2f),
            w[TextView] <~ txt("TTR Diff") <~ l_weight(0.2f),
            w[TextView] <~ txt("Neuer TTR") <~ l_weight(0.2f)
          ),
        w[TextView] <~ lp[LinearLayout](MATCH_PARENT, 1 dp) <~ BgTweaks.color(Color.WHITE),
        w[ListView] <~ ttrDataListable.listAdapterTweak(ttrData.flatten)
      ) <~ padding(left = 4 dp, right = 4 dp)
      }
    }
  }

}
