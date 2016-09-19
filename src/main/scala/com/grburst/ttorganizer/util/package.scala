package com.grburst.ttorganizer.util

import scala.util.Try

import android.view.{ View, ViewGroup }
import android.widget.{ TextView, EditText }

package object androidHelper {

  implicit class StringHelper(s: String) {
    def toIntOption: Option[Int] = Try(s.toInt).toOption
  }

  implicit class ViewHelper(vg: ViewGroup) {

    def getChildren: List[View] = {
      ((0 until vg.getChildCount()) map { i =>
        vg.getChildAt(i)
      }).toList
    }

    def getFlatChildren: List[View] = {
      getChildren.flatMap {
        case v: ViewGroup => v.getFlatChildren
        case v: View => Some(v)
      }
    }

    def getTextChildren: List[TextView] = {
      getFlatChildren.collect { case c: TextView => c }
    }

    def getInputTextChildren: List[EditText] = {
      getFlatChildren.collect { case c: EditText => c }
    }
  }

  // implicit class TextViewHelper(t: TextView) {

  // }

}
