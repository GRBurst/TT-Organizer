package com.grburst.ttorganizer.activities

import com.grburst.ttorganizer.libtt.parser.LeagueRankingParser
import com.grburst.ttorganizer.libtt.Player
import com.grburst.ttorganizer.util.Styles

import org.scaloid.common._

import android.app.Activity
import android.os.Bundle
import android.widget.{ TextView, ListView }

import macroid._
import macroid.FullDsl._
import macroid.contrib.Layouts._ // [x]LinearLayout

import macroid.viewable._ //Listable
import macroid.Transformer.Layout

class LeagueRankingActivity extends SActivity with Contexts[Activity] with Styles {

  def ttPlayerListable(): Listable[Player, HorizontalLinearLayout] =
    Listable[Player].tr {
      l[HorizontalLinearLayout](
        w[TextView] <~ l_weight(0.2f),
        w[TextView] <~ l_weight(0.2f),
        w[TextView] <~ l_weight(0.2f),
        w[TextView] <~ l_weight(0.2f))
    }(player => Transformer {
      case Layout(w1: TextView, w2: TextView, w3: TextView, w4: TextView) =>
        Ui.sequence(
          w1 <~ text(s"${player.dRank}"),
          w2 <~ text(s"${player.name}"), // <~ On.click(...),
          w3 <~ text(s"${player.club}"),
          w4 <~ text(s"${player.ttr}"))
    })

  override def onCreate(savedInstanceState: Bundle) = {
    super.onCreate(savedInstanceState)

    val lrParser = LeagueRankingParser()

    setContentView {
      (l[VerticalLinearLayout](
        w[TextView] <~ text(s"MyTischtennis.de event parser"),
        w[TextView] <~ text(lrParser.get().head.toString),
        w[ListView] <~ ttPlayerListable.listAdapterTweak(lrParser.get())) <~ padding(left = 4 dp, right = 4 dp)).get
    }

  }
}
