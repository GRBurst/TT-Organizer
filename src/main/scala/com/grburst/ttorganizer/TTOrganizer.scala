package com.grburst.ttorganizer

import com.grburst.ttorganizer.activities._
import android.app.Activity
import android.os.Bundle

import org.scaloid.common._

import macroid._

class TTOrganizer extends SActivity {

  override def onCreate(savedInstanceState: Bundle) = {
    super.onCreate(savedInstanceState)

    contentView = new SVerticalLayout {
      SButton("Ergebnisse", SIntent[EventsActivity].start[EventsActivity])
      SButton("Ergebnisse-Detail", SIntent[EventDetailActivity].start[EventDetailActivity])
      SButton("Statistiken")
      SButton("Favoriten")
      SButton("Vereinsrangliste", SIntent[ClubRankingActivity].start[ClubRankingActivity])
      SButton("Liga-Rangliste", SIntent[LeagueRankingActivity].start[LeagueRankingActivity])
      SButton("TTR-Rechner", SIntent[CalculatorActivity].start[CalculatorActivity])
      SButton("Spielsimulation")
      SButton("Suche")
    }



  }

}
