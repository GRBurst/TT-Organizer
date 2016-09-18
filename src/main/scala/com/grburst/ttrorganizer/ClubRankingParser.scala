package com.grburst.ttrorganizer

import scala.util.Try

import android.net.Uri

import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.model.Element
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL.Parse._

case class TTPlayer(playerId:Int,
  rank:String,
  dRank:Int,
  name:String,
  club:String,
  clubId:Int,
  ttr:Int)


  // val urlPattern = "http://www.mytischtennis.de/community/ajax/_rankingList?kontinent=Europa&land=DE&deutschePlusGleichgest=no&alleSpielberechtigen=&verband=&bezirk=&kreis=&regionPattern123=&regionPattern4=&regionPattern5=&geschlecht=&geburtsJahrVon=&geburtsJahrBis=&ttrVon=&ttrBis=&ttrQuartalorAktuell=aktuell&anzahlErgebnisse=100&vorname=&nachname=&verein=&vereinId=&vereinPersonenSuche=&vereinIdPersonenSuche=&ligen=&groupId=&showGroupId=&deutschePlusGleichgest2=no&ttrQuartalorAktuell2=aktuell&showmyfriends=0'"
case class ClubRankingParser(url:String = "/storage/emulated/0/mytischtennis.de/myclub-ajax.htm") {

  def strToInt(s:String):Option[Int] = Try(s.toInt).toOption

  val browser = JsoupBrowser()
  val eventDoc = browser.parseFile(url)

  def get():List[TTPlayer] = {

    val ttrTable = eventDoc >> element(".table-mytt") >> elementList("tr")
    val ttrData: List[Option[TTPlayer]] = ttrTable.map(x => (x >> elementList("td")).toList match {
      case List(r,d,n,c,t,s) => {
        val pId:Array[String] = (n >> attr("data-tooltipdata")("a")).split(";")
        val uri = Uri.parse(c >> attr("href")("a"));

        Some(TTPlayer(pId(0).toInt, r.text, strToInt(d.text).getOrElse(-1), pId(2), c.text, uri.getQueryParameter("clubid").toInt, strToInt(t.text).getOrElse(-1)))
      }
      case _ => None
    })

  ttrData.flatten

  }

}
