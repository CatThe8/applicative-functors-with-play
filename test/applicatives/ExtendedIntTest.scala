package applicatives

import model.applicatives.implicits.extendInt
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.test.Injecting

import scala.language.implicitConversions

class HomeControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  "HomeController GET" should {

    "render the index page from a new instance of controller" in {
      val res = 4.plus(3)

      res mustBe 7
    }
  }
}

