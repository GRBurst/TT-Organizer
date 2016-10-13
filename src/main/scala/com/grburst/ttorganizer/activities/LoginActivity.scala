package com.grburst.ttorganizer.activities

import com.grburst.spraytest._
import com.grburst.ttorganizer.StateManager
import com.grburst.ttorganizer.util.Styles
import com.grburst.ttorganizer.util._

import com.grburst.libtt.MyTischtennisBrowser
import com.grburst.libtt.types.User
import com.grburst.ttorganizer.util.tweaks._

import scala.concurrent.{ ExecutionContext, Future }
import scala.util.{ Try, Success, Failure }

import akka.actor.ActorSystem

import android.app.Activity
import android.text.InputType
import android.content.Intent
import android.os.{ Bundle, AsyncTask }
import android.widget._

// import org.scaloid.common.{ SActivity, SIntent }
import org.scaloid.common.{ toast => stoast, _ }

import macroid._
import macroid.FullDsl._
import macroid.Contexts
import macroid.contrib.Layouts._

class LoginActivity extends SActivity with Contexts[Activity] with Styles {
  // lazy implicit val exec = ExecutionContext.fromExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
  // implicit val system = ActorSystem()
  // import system.dispatcher
  // implicit val system = ActorSystem("libtt-ttorganizer", libttConf)
  val passwordField = Tweak[TextView](f => f.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD))

  override def onCreate(savedInstanceState: Bundle) = {
    super.onCreate(savedInstanceState)

    // implicit val user = StateManager.user
    implicit val ec = ExecutionContext.fromExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    implicit val system = ActorSystem("libtt-actors")

    var username = slot[EditText]
    var password = slot[EditText]

    setContentView {
      (l[VerticalLinearLayout](
        w[TextView] <~ text("MyTischtennis.de login data"),
        l[HorizontalLinearLayout](
          w[TextView] <~ text("Username: ") <~ l_weight(0.3f),
          // w[EditText] <~ l_weight(0.7f) <~ wire(username)))).get
          w[EditText] <~ l_weight(0.7f) <~ wire(username)),
        l[HorizontalLinearLayout](
          w[TextView] <~ text("Password: ") <~ l_weight(0.3f),
          w[EditText] <~ passwordField <~ l_weight(0.7f) <~ wire(password) <~ text("~YjXD2EeheQ_V@$X]QT94MhNzm<.*N/Q")),
        w[Button] <~ text("Login") <~ On.click {
          Ui {
            // val futureUser = MyTischtennisBrowser().login(username.get.getText.toString, password.get.getText.toString)
            // futureUser onComplete {
            //   case Success(u) => {
            //     println("successfully received user")
            //     StateManager.user = u //Store
            //     StateManager.username = username.get.getText.toString
            //     StateManager.pass = password.get.getText.toString
            //     SIntent[OrganizerActivity].start[OrganizerActivity]
            //   }
            //   case Failure(e) => { toast("Could not login or get user") <~ fry }.run
            // }
            val b = Browser()
            b.getMtEverestElevation onComplete {
              case Success(e) => {
                println("successfully received elevation")
                StateManager.user = User(1, e.toString, "", Map()) //Store
                SIntent[OrganizerActivity].start[OrganizerActivity]
              }
              case Failure(e) => { toast("Could not get elevation") <~ fry }.run
            }
            // { toast("Could not get elevation") <~ fry }.run
            // val hello = MyTischtennisBrowser().hello()
            // hello onComplete {
            //   case Success(h) => {
            //     println("successfully received user")
            //     StateManager.user = User(1, "", "", Map()) //Store
            //     toast("Could not login or get user") <~ fry
            //     SIntent[OrganizerActivity].start[OrganizerActivity]
            //   }
            //   case Failure(e) => { toast("Could not login or get user") <~ fry }.run
            // }
          }
        })).get
    }
  }
}
