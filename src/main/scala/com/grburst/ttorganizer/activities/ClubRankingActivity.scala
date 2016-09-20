package com.grburst.ttorganizer.activities

import com.grburst.ttorganizer.libtt.parser.ClubRankingParser
import com.grburst.ttorganizer.libtt.Player
import com.grburst.ttorganizer.util.ttHelper._
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

class ClubRankingActivity extends SActivity with Contexts[Activity] with Styles {

  override def onCreate(savedInstanceState: Bundle) = {
    super.onCreate(savedInstanceState)

    val lrParser = ClubRankingParser()

    setContentView {
      (l[VerticalLinearLayout](
        w[TextView] <~ text(s"MyTischtennis.de event parser"),
        w[TextView] <~ text(lrParser.get().head.toString),
        w[ListView] <~ playerListable.listAdapterTweak(lrParser.get())) <~ padding(left = 4 dp, right = 4 dp)).get
    }

  }
}
