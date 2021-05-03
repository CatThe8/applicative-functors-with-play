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

  private val future1 = Future {
    Thread.sleep(1000)
    println("Waiting for 1")
    1
  }

  private val future2 = Future {
    Thread.sleep(3000)
    println("Waiting for 2")
    2
  }

  private val future3 = Future {
    Thread.sleep(1000)
    println("Waiting for 3")
    "3"
  }

  private val future4 = Future {
    Thread.sleep(2000)
    println("Waiting for 4")
    4
  }

  "Applicative Functor extension" should {

    "applicatives with apply over" in {
      val function: Int => Int => String => String = { (x: Int) => y: Int => z: String => s"$x - $y - $z" }

      val applicativeResult = Future(function)
        .applyOver(future1)
        .applyOver(future2)
        .applyOver(future3)

      val result = Await.result(
        applicativeResult, 10 seconds
      )

      println(result)
    }

    "applicatives with zip with" in {
      val function: Int => Int => String => String = { (x: Int) => y: Int => z: String => s"$x - $y - $z" }

      val applicativeResult = Future(function)
        .zipOver(future1)
        .zipOver(future2)
        .zipOver(future3)

      val result = Await.result(
        applicativeResult, 10 seconds
      )

      println(result)
    }

    "applicatives with zip with and apply over" in {
      val function = { x: Int => y: Int => z: String => x1: Int => s"$x - $y - $z - $x1" }

      val applicativeResult = Future(function)
        .zipOver(future1)
        .applyOver(future2)
        .applyOver(future3)
        .applyOver(future4)

      val result = Await.result(
        applicativeResult, 10 seconds
      )

      print(for {
        v1 <- future1
        v2 <- future2
        v3 <- future3
        v4 <- future4
      } function(v1)(v2)(v3)(v4)
      )

      println(result)
    }

    "applicatives with for comprehension" in {
      val function = { x: Int => y: Int => z: String => x1: Int => s"$x - $y - $z - $x1" }

      val result = for {
        v1 <- future1
        v2 <- future2
        v3 <- future3
        v4 <- future4
      } yield function(v1)(v2)(v3)(v4)

      Await.result(
        result, 10 seconds
      )
    }
  }
}

