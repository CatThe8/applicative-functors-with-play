package applicatives

import model.applicatives.ImplicitsFutures.extendedFuture
import org.scalatest.time.SpanSugar.convertIntToGrainOfTime
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.test.Injecting

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.language.{implicitConversions, postfixOps}

class ApplicativeFunctorTest extends PlaySpec with GuiceOneAppPerTest with Injecting {

  def makeFuture[T](x: T) = Future {
    println(s"Waiting for $x")
    Thread.sleep(2000)
    println(s"Sleeping $x")
    x
  }

  "Applicative Functor extension" should {

    "applicatives with apply over" in {
      val function = { (x: Int) => y: Int => z: String => s"$x - $y - $z" }

      val applicativeResult = Future(function)
        .applyOver(makeFuture(1))
        .applyOver(makeFuture(2))
        .applyOver(makeFuture("3"))

      val result = Await.result(
        applicativeResult, 10 seconds
      )

      println(result)
    }

    "applicatives with applyOver and mixed with for comprehension" in {
      val function = { x1: Int =>
        x2: Int =>
          x3: String =>
            x4: Int =>
              x5: String =>
                x6: Int => s"$x1 - $x2 - $x3 - $x4 - $x5 - $x6"
      }

      val flatMapResult = makeFuture(1).flatMap(x1 =>
        makeFuture(2).flatMap(x2 =>
          makeFuture("3").flatMap(x3 =>
            makeFuture(4).flatMap(x4 =>
              makeFuture("5").flatMap(x5 =>
                makeFuture(6).map(x6 =>
                  s"$x1 - $x2 - $x3 - $x4 - $x5 - $x6"
                )
              )
            )
          )
        )
      )

      val resultWithFlatMap = Await.result(
        flatMapResult, 30 seconds
      )

      println(s"result with resultWithFlatMap: $resultWithFlatMap")

      val applicativeResultWithFor = for {
        v1 <- makeFuture(1)
        v2 <- makeFuture(2)
        v3 <- makeFuture("3")
        v4 <- makeFuture(4)
        v5 <- makeFuture("5")
        v6 <- makeFuture(6)
      } yield function(v1)(v2)(v3)(v4)(v5)(v6)

      val resultWithFor = Await.result(
        applicativeResultWithFor, 20 seconds
      )

      flatMap { x => flatMap (y) => }

      println(s"result with for applicativeResultWithFor: $resultWithFor")

      val applicativeResult = Future(function)
        .applyOver(makeFuture(1))
        .zipOver(makeFuture(2))
        .applyOver(makeFuture("3"))
        .zipOver(makeFuture(4))
        .applyOver(makeFuture("5"))
        .applyOver(makeFuture(6))

      val resultWithZipAndApply = Await.result(
        applicativeResult, 30 seconds
      )

      println(s"result with resultWithZipAndApply: $resultWithZipAndApply")

    }
  }
}

