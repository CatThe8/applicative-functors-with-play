package model.applicatives

import scala.language.implicitConversions

class ExtendedInt(value: Int) {
  def plus(other:Int): Int = value + other

}

object ImplicitsInt {
  implicit def extendInt(i: Int): ExtendedInt = new ExtendedInt(i)
}
