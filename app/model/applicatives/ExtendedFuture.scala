package model.applicatives

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.language.implicitConversions

class ExtendedFuture[A, B](applicativeFunctor: Future[A => B]) {
  def applyOver(other: Future[A]): Future[B] = applicativeFunctor.flatMap(
    f => other.map(f(_))
  )

  def zipOver(other: Future[A]): Future[B] = applicativeFunctor.zipWith(other)(_.apply(_))
}

object ImplicitsFutures {
  implicit def extendedFuture[A, B](f: Future[A => B]): ExtendedFuture[A, B] = new ExtendedFuture(f)
}
