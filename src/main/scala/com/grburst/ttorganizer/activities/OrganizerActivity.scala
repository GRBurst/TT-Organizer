package com.grburst.ttorganizer.activities

import com.grburst.ttorganizer.StateManager

import com.grburst.libtt.MyTischtennisBrowser

import scala.concurrent.{ ExecutionContext, Future }

import android.app.Activity
import android.os.{ Bundle, AsyncTask }
import android.widget._

import org.scaloid.common._

import macroid._
import macroid.FullDsl._
import macroid.Contexts
import macroid.contrib.Layouts._

// class OrganizerActivity(implicit user: User) extends SActivity {
// class OrganizerActivity extends OrganizerBaseApp extends SActivity with Contexts[Activity] {
// class OrganizerActivity extends SActivity with OrganizerBaseApp with Contexts[Activity] {
class OrganizerActivity extends SActivity with Contexts[Activity] {
  // lazy implicit val exec = ExecutionContext.fromExecutor(AsyncTask.THREAD_POOL_EXECUTOR)

  override def onCreate(savedInstanceState: Bundle) = {
    super.onCreate(savedInstanceState)

    // implicit val ec = ExecutionContext.fromExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    // implicit val user = StateManager.user
    // import StateManager.system

    if (StateManager.user.id == 0) SIntent[LoginActivity].start[LoginActivity]

    setContentView {
      (l[VerticalLinearLayout](
        // w[Button] <~ text("Ergebnisse") <~ On.click {
        //   Ui { SIntent[EventsActivity].start[EventsActivity] }
        // },
        w[TextView] <~ text(StateManager.user.toString))).get

      // contentView = new SVerticalLayout {
      // SButton("Ergebnisse", )
      // SButton("Ergebnisse-Detail", SIntent[EventDetailActivity].start[EventDetailActivity])
      // SButton("Statistiken")
      // SButton("Favoriten")
      // SButton("Vereinsrangliste", SIntent[ClubRankingActivity].start[ClubRankingActivity])
      // SButton("Liga-Rangliste", SIntent[LeagueRankingActivity].start[LeagueRankingActivity])
      // SButton("TTR-Rechner", SIntent[CalculatorActivity].start[CalculatorActivity])
      // SButton("Spielsimulation")
      // SButton("Suche", SIntent[SearchActivity].start[SearchActivity])
    }

  }

}
