package com.grburst.ttrorganizer

case class TTMatch(
  opponent: String,
  oTTR: Int,
  oId: Int,
  result: String,
  set1: String,
  set2: String,
  set3: String,
  set4: String,
  set5: String,
  set6: String,
  set7: String,
  ge: Float)

case class TTPlayer(
  playerId: Int,
  rank: String,
  dRank: Int,
  name: String,
  club: String,
  clubId: Int,
  ttr: Int)
