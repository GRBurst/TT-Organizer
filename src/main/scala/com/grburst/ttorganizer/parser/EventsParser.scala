package com.grburst.ttorganizer.parser

import com.grburst.ttorganizer.util.TTEvent

import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.model.Element
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL.Parse._

// url = https://www.mytischtennis.de/community/events
case class EventsParser(url:String = "/storage/emulated/0/mytischtennis.de/events") {

  val browser = JsoupBrowser()
  val eventDoc = browser.parseFile(url)

  val currTTR = eventDoc >> element(".ttr-box") >> text("h3")

  def get():List[TTEvent] = {

    val ttrTable = eventDoc >> element("#tooltip-wrapper-ttr-table") >> element("tbody") >> elementList("tr")
    val ttrData: List[Option[TTEvent]] = ttrTable.map(x => (x >> elementList("td")).toList match {
      case List(s,l,e,a,b,g,ty,tr,td) => {
        val eIdt:String = e >> attr("href")("a")
        val eId = eIdt.substring(eIdt.indexOf("(") + 1, eIdt.indexOf(",",eIdt.indexOf("(")))
        Some(TTEvent(s.text,l.text,e.text,eId.toInt,a.text,b.text,g.text,ty.text,tr.text.toInt,td.text.toInt))
      }
      case _ => None
    })

  ttrData.flatten

  }

}
