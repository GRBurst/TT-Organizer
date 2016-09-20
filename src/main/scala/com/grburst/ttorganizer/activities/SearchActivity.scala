package com.grburst.ttorganizer.activities

import com.grburst.ttorganizer.libtt.parser.SearchParser
import com.grburst.ttorganizer.libtt.Player
import com.grburst.ttorganizer.util.Styles
import com.grburst.ttorganizer.fragments.RankingFragment

import org.scaloid.common._

import android.app.{ Activity, Fragment }
import android.os.Bundle
import android.widget.{ TextView, ListView }

import macroid._
import macroid.FullDsl._
import macroid.contrib.Layouts._ // [x]LinearLayout
import macroid.viewable._ //Listable
import macroid.IdGenerator
// import macroid.{ Contexts, IdGenerator }

import scala.collection.JavaConverters._

object Id extends IdGenerator(1000)

class SearchActivity extends SActivity with Contexts[Activity] with Styles {

  override def onCreate(savedInstanceState: Bundle) = {
    super.onCreate(savedInstanceState)

    val bundle = new Bundle()
    bundle.putString("type", "Search Ranking")

    setContentView {
      (l[VerticalLinearLayout](
        w[TextView] <~ text(s"MyTischtennis.de"),
        // TODO: Spinner for ranking choice? w[Spinner]
        f[RankingFragment].pass(bundle).framed(Id.searchRanking, Tag.ranking)) <~ padding(left = 4 dp, right = 4 dp)).get
    }

  }

}
