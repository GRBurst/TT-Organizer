package com.grburst.ttrorganizer

import android.app.Activity
import android.os.Bundle

import org.scaloid.common._

import macroid._

class TTROrganizer extends SActivity with Contexts[SActivity] {

  override def onCreate(savedInstanceState: Bundle) = {
    super.onCreate(savedInstanceState)

    contentView = new SVerticalLayout {
      SButton("Ergebnisse", SIntent[Events].start[Events])
      SButton("Ergebnisse-Detail", SIntent[EventDetail].start[EventDetail])
      SButton("Statistiken")
      SButton("Favoriten")
      SButton("Liga-Rangliste")
      SButton("Vereinsrangliste", SIntent[ClubRanking].start[ClubRanking])
      SButton("TTR-Rechner", SIntent[TTRCalculator].start[TTRCalculator])
      SButton("Spielsimulation")
      SButton("Suche")
    }



  }

}
