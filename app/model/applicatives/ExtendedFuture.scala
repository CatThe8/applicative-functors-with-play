package model.applicatives

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.language.implicitConversions

class ExtendedFuture[A, B](applicativeFunctor: Future[A => B]) {
  def applyOver(other: Future[A]): Future[B] = applicativeFunctor.flatMap(
    f => other.map(f(_))
  )

  def zipOver(other: Future[A]): Future[B] = applicativeFunctor.
    zipWith(other)({ (f: A => B, r: A) => f(r) })
}

object implicits {
  implicit def extendedFuture[A, B](f: A => B): Future[A => B] = Future(f)
}
