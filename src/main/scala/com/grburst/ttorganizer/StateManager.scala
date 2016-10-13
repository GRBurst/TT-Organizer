package com.grburst.ttorganizer

import com.grburst.libtt.types.User
import spray.http.HttpCookie
import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory

// import akka.http.scaladsl.model.headers.HttpCookie

// case class StateManager() {

//   var user: User = User(0, "", "")

// }

object StateManager {

  // val libttConf = ConfigFactory.parseString("""
  //   spray.can.client.user-agent-header = "Mozilla/5.0 (X11; Linux x86_64; rv:50.0) Gecko/20100101 Firefox/50.0"
  //   spray.can.host-connector.max-redirects = 0""")

  // implicit val system = ActorSystem("libtt-actors")

  var user: User = User(0, "", "", Map())
  var username: String = "";
  var pass: String = "";

}
