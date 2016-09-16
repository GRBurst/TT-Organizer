package com.grburst.ttrorganizer

import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.model.Element
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL.Parse._

case class TTREvent(sDate:String,
  lDate:String,
  name:String,
  id:Int,
  ak:String,
  bilanz:String,
  gewinnerwartung:String,
  typ:String,
  ttr:Int,
  ttrDiff:Int)

// url = https://www.mytischtennis.de/community/events
case class EventsParser(url:String = "/storage/emulated/0/mytischtennis.de/events") {

  val browser = JsoupBrowser()
  val eventDoc = browser.parseFile(url)

  val currTTR = eventDoc >> element(".ttr-box") >> text("h3")

  def events():List[TTREvent] = {

    val ttrTable = eventDoc >> element("#tooltip-wrapper-ttr-table") >> element("tbody") >> elementList("tr")
    val ttrData: List[Option[TTREvent]] = ttrTable.map(x => (x >> elementList("td")).toList match {
      case List(s,l,e,a,b,g,ty,tr,td) => {
        val eIdt:String = e >> attr("href")("a")
        val eId = eIdt.substring(eIdt.indexOf("(") + 1, eIdt.indexOf(",",eIdt.indexOf("(")))
        Some(TTREvent(s.text,l.text,e.text,eId.toInt,a.text,b.text,g.text,ty.text,tr.text.toInt,td.text.toInt))
      }
      case _ => None
    })

  ttrData.flatten

  }

}
