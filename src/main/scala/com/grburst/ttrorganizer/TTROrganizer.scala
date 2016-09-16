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
import macroid.FullDsl.{text => txt, id => mid, toast => mtoast, _}
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

class TTROrganizer extends SActivity with Styles with IdGeneration with Contexts[Activity] {

  override def onCreate(savedInstanceState: Bundle) = {
    super.onCreate(savedInstanceState)

    contentView = new SVerticalLayout {
      SButton("Ergebnisse", SIntent[Events].start[Events])
      SButton("Ergebnisse-Detail", SIntent[EventDetail].start[EventDetail])
      SButton("Statistiken")
      SButton("Favoriten")
      SButton("Vereinsrangliste")
      SButton("Liga-Rangliste")
      SButton("TTR-Rechner")
      SButton("Spielsimulation")
      SButton("Suche")
    }



  }

}
