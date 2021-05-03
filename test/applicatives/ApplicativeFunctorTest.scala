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

  "HomeController GET" should {

    "applicatives with apply over" in {
      val function: Int => Int => String => String = { (x: Int) => y: Int => z: String => s"$x - $y - $z" }
      val f1 = Future(3)
      val f2 = Future(4)
      val f3 = Future("5")

      val applicativeResult = Future(function)
        .applyOver(f1)
        .applyOver(f2)
        .applyOver(f3)

      val result = Await.result(
        applicativeResult, 10 seconds
      )

      println(result)
    }

    "applicatives with zip with" in {
      val function: Int => Int => String => String = { (x: Int) => y: Int => z: String => s"$x - $y - $z" }
      val f1 = Future(3)
      val f2 = Future(4)
      val f3 = Future("5")

      val applicativeResult = Future(function)
        .zipOver(f1)
        .zipOver(f2)
        .zipOver(f3)

      val result = Await.result(
        applicativeResult, 10 seconds
      )

      println(result)
    }

    "applicatives with zip with and apply over" in {
      val function = { x: Int => y: Int => z: String => x1: Int => s"$x - $y - $z - $x1" }
      val f1 = Future {
        Thread.sleep(1000)
        println("Waiting for 1")
        1
      }
      val f2 = Future {
        Thread.sleep(3000)
        println("Waiting for 2")
        2
      }
      val f3 = Future {
        Thread.sleep(1000)
        println("Waiting for 3")
        "3"
      }
      val f4 = Future {
        Thread.sleep(2000)
        println("Waiting for 4")
        4
      }

      val applicativeResult = Future(function)
        .zipOver(f1)
        .applyOver(f2)
        .applyOver(f3)
        .applyOver(f4)

      val result = Await.result(
        applicativeResult, 10 seconds
      )

      print(for {
        v1 <- f1
        v2 <- f2
        v3 <- f3
        v4 <- f4
      } function(v1)(v2)(v3)(v4)
      )

      println(result)
    }

    "applicatives with for comprehension" in {
      val function = { x: Int => y: Int => z: String => x1: Int => s"$x - $y - $z - $x1" }
      val f1 = Future {
        Thread.sleep(1000)
        println("Waiting for 1")
        1
      }
      val f2 = Future {
        Thread.sleep(3000)
        println("Waiting for 2")
        2
      }
      val f3 = Future {
        Thread.sleep(1000)
        println("Waiting for 3")
        "3"
      }
      val f4 = Future {
        Thread.sleep(2000)
        println("Waiting for 4")
        4
      }

      val result = for {
        v1 <- f1
        v2 <- f2
        v3 <- f3
        v4 <- f4
      } yield function(v1)(v2)(v3)(v4)

      Await.result(
        result, 10 seconds
      )
    }

  }
}

