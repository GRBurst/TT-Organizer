package com.grburst.ttorganizer.libtt.parser

import com.grburst.ttorganizer.libtt.Player
import com.grburst.ttorganizer.util.androidHelper.StringHelper

import scala.util.Try
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import android.net.Uri

import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.model.Element
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL.Parse._

case class SearchParser(url: String = "/storage/emulated/0/mytischtennis.de/myclub-ajax.htm") {

  def strToInt(s: String): Option[Int] = Try(s.toInt).toOption

  val browser = JsoupBrowser()
  val eventDoc = browser.parseFile(url)

  val get: Future[List[Player]] = Future {

    val ttrTable = eventDoc >> element(".table-mytt") >> elementList("tr")
    val ttrData: List[Option[Player]] = ttrTable.map(x => (x >> elementList("td")).toList match {
      case List(r, d, n, c, t, s) => {
        val pId: Array[String] = (n >> attr("data-tooltipdata")("a")).split(";")
        val uri = Uri.parse(c >> attr("href")("a"));

        Some(Player(pId(0).toInt, r.text, d.text.toIntOption.getOrElse(-1), pId(2), c.text, uri.getQueryParameter("clubid").toInt, t.text.toIntOption.getOrElse(-1)))
      }
      case _ => None
    })

    ttrData.flatten

  }

}
