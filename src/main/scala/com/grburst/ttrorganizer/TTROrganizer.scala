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

import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.model.Element
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL.Parse._

case class Event(sDate:String
  , lDate:String
  , event:String
  , ak:String
  , bilanz:String
  , gewinnerwartung:String
  , typ:String
  , ttr:Int
  , ttrDiff:Int)
  {

  }

trait Styles {

  def ttrDataListable(implicit ctx: ActivityContext, appCtx: AppContext):Listable[Event, HorizontalLinearLayout] =
    Listable[Event].tr{
      l[HorizontalLinearLayout](
        w[TextView] <~ padding(left = 4 dp), //<~ lp[LinearLayout](MATCH_PARENT, WRAP_CONTENT, 0.2f),
        w[TextView] <~ padding(left = 4 dp),
        w[TextView] <~ padding(left = 4 dp),
        w[TextView] <~ padding(left = 4 dp),
        w[TextView] <~ padding(left = 4 dp)
      )
    } (event => Transformer {
      case Layout(w1: TextView, w2: TextView, w3: TextView, w4: TextView, w5: TextView ) =>
        Ui.sequence(
          w1 <~ txt(s"${event.sDate}") <~ On.click(w1 <~ hide),
          w2 <~ txt(s"${event.event}") <~ On.click(w2 <~ hide),
          w3 <~ txt(s"${event.bilanz}") <~ On.click(w3 <~ hide),
          w4 <~ txt(s"${event.ttr}") <~ On.click(w4 <~ hide),
          w5 <~ txt(s"${event.ttrDiff}") <~ On.click(w5 <~ hide)
        )
    })

}

class TTROrganizer extends Activity with Styles with IdGeneration with Contexts[Activity] {

  override def onCreate(savedInstanceState: Bundle) = {
    super.onCreate(savedInstanceState)

    var ts = slot[TextView]

    val browser = JsoupBrowser()
    val doc = browser.parseFile("/storage/emulated/0/mytischtennis.de/events")

    val ttrData: List[Option[Event]] = (doc >> element("#tooltip-wrapper-ttr-table") >> element("tbody") >> elementList("tr")).map(x => (x >> texts("td")).toList match {
      case List(s,l,e,a,b,g,ty,tr,td) => Some(Event(s,l,e,a,b,g,ty,tr.toInt,td.toInt))
      // case de if (de >> element(".events-container") >> element(".events-data") >> element("tbody") ) => None
      // case de => (de >> element(".events-container") >> element(".events-data") >> element("tbody") )
      case _ => None
    })

    setContentView {
      getUi {
        l[LinearLayout](
          w[TextView] <~ wire(ts) <~ Some(txt("MyTischtennis.de event parser")),
          w[ListView] <~ ttrDataListable.listAdapterTweak(ttrData.flatten)
          // , l[ScrollView](
          //     l[TableLayout](
          //       ttrData.flatten.map{
          //         e =>
          //           l[TableRow](
          //             w[TextView] <~ txt(e.sDate) <~ layoutParams[TableRow](0)
          //             , w[TextView] <~ txt(e.event) <~ layoutParams[TableRow](1) <~ padding(left = 4 dp)
          //             , w[TextView] <~ txt(e.bilanz) <~ layoutParams[TableRow](2)
          //             , w[TextView] <~ txt(e.ttr.toString) <~ layoutParams[TableRow](3)
          //             , w[TextView] <~ txt(e.ttrDiff.toString) <~ layoutParams[TableRow](4)
          //           ) <~ mid(Id.tableRow) <~ padding(top = 8 dp)
          //       }:_*
          //       // <~ ttrDataListable.listAdapterTweak(ttrData.flatten)
          //     ) //<~ layoutParams[TableLayout](shrinkColumns("1"))
          // )
        ) <~ vertical
      }
    }
  }

}
