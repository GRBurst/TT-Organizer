package com.grburst.ttorganizer.activities

import com.grburst.ttorganizer.StateManager
import com.grburst.ttorganizer.util.ttHelper._
import com.grburst.ttorganizer.util.Styles
import com.grburst.ttorganizer.util.tweaks._

import com.grburst.libtt.MyTischtennisBrowser
import com.grburst.libtt.types.Player

import scala.concurrent.ExecutionContext

import android.app.Activity
import android.os.{ Bundle, AsyncTask }
import android.widget.{ TextView, ListView }

import org.scaloid.common._

import macroid._
import macroid.FullDsl._
import macroid.contrib.Layouts._ // [x]LinearLayout
import macroid.viewable._ //Listable
import macroid.Transformer.Layout

class ClubRankingActivity extends SActivity with Contexts[Activity] with Styles {

  def rankingListable: Listable[Player, HorizontalLinearLayout] =
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
          w2 <~ text(s"${player.firstname} ${player.surname}"), // <~ On.click(...),
          w3 <~ text(s"${player.club}"),
          w4 <~ text(s"${player.ttr}"))
    })

  override def onCreate(savedInstanceState: Bundle) = {
    super.onCreate(savedInstanceState)

    // implicit val ec = ExecutionContext.fromExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    // implicit val user = StateManager.user
    // import StateManager.system

    setContentView {
      (l[VerticalLinearLayout](
        w[TextView] <~ text("MyTischtennis.de event parser"))).get
      // w[TextView] <~ text("MyTischtennis.de event parser"),
      // w[TextView] <~ text(lrParser.get().head.toString),
      // w[ListView] <~ playerListable.listAdapterTweak(parser.getMyClubRanking)) <~ padding(left = 4 dp, right = 4 dp)).get
      // w[ListView] <~ browser.getMyClubRanking(StateManager.user).foreach(rankingListable.listAdapterTweak(_)))).get
      // w[ListView] <~ MyTischtennisBrowser().getMyClubRanking.map(l => rankingListable.listAdapterTweak(l)))).get
    }

  }
}
