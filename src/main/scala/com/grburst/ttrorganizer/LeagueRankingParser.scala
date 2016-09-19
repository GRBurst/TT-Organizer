package com.grburst.ttrorganizer
import com.grburst.ttrorganizer.androidHelper._

import scala.util.Try

import android.net.Uri

import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.model.Element
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL.Parse._

// val urlPattern = "http://www.mytischtennis.de/community/ajax/_rankingList?kontinent=Europa&land=DE&deutschePlusGleichgest=no&alleSpielberechtigen=&verband=&bezirk=&kreis=&regionPattern123=&regionPattern4=&regionPattern5=&geschlecht=&geburtsJahrVon=&geburtsJahrBis=&ttrVon=&ttrBis=&ttrQuartalorAktuell=aktuell&anzahlErgebnisse=100&vorname=&nachname=&verein=&vereinId=&vereinPersonenSuche=&vereinIdPersonenSuche=&ligen=&groupId=&showGroupId=&deutschePlusGleichgest2=no&ttrQuartalorAktuell2=aktuell&showmyfriends=0'"
case class LeagueRankingParser(url: String = "/storage/emulated/0/mytischtennis.de/league-ajax.htm") {

  val browser = JsoupBrowser()
  val eventDoc = browser.parseFile(url)

  def get(): List[TTPlayer] = {

    val ttrTable = eventDoc >> element(".table-mytt") >> elementList("tr")
    val ttrData: List[Option[TTPlayer]] = ttrTable.map(x => (x >> elementList("td")).toList match {
      case List(r, d, n, c, t, s) => {
        val pId: Array[String] = (n >> attr("data-tooltipdata")("a")).split(";")
        val uri = Uri.parse(c >> attr("href")("a"));

        Some(TTPlayer(pId(0).toInt, r.text, d.text.toIntOption.getOrElse(-1), pId(2), c.text, uri.getQueryParameter("clubid").toInt, t.text.toIntOption.getOrElse(-1)))
      }
      case _ => None
    })

    ttrData.flatten

  }

}