package com.grburst.ttorganizer.fragments

import com.grburst.ttorganizer.libtt.parser.SearchParser
import com.grburst.ttorganizer.libtt.Player
import com.grburst.ttorganizer.util.ttHelper._

import org.scaloid.common._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import android.app.Fragment
import android.os.Bundle
import android.widget.{ TextView, ListView }
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import macroid._
import macroid.FullDsl._
import macroid.Contexts
import macroid.contrib.Layouts._ // [x]LinearLayout
import macroid.viewable._ //Listable
import macroid.Transformer.Layout

class RankingFragment extends Fragment with Contexts[Fragment] {

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
          w2 <~ text(s"${player.name}"), // <~ On.click(...),
          w3 <~ text(s"${player.club}"),
          w4 <~ text(s"${player.ttr}"))
    })

  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle) = {

    // val parser: SearchParser = getArguments()
    val parser: SearchParser = SearchParser()

    (l[VerticalLinearLayout](
      w[TextView] <~ text(getArguments().getString("type")),
      w[ListView] <~ parser.get.map(l => rankingListable.listAdapterTweak(l)))).get

  }
}
