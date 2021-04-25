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

    "render the index page from a new instance of controller" in {
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
  }
}

