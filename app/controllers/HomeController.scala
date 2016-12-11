package controllers

import java.net.ConnectException
import javax.inject._

import org.jsoup.Jsoup
import play.api.libs.ws._
import play.api.mvc._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.Configuration

import scala.concurrent.Future

@Singleton
class HomeController @Inject()(
                                ws: WSClient,
                                config: Configuration
                              )
  extends Controller {

  private type WSMessage = String

  private val logger = org.slf4j.LoggerFactory.getLogger(this.getClass)

  //  private implicit val logging = Logging(actorSystem.eventStream, logger.getName)

  def index: Action[AnyContent] = Action.async {
    val response = for {
      muiFragment <- getBodyOf(config.underlying.getString("frontendFragmentsUrls.mui"))
      madFragment <- getBodyOf(config.underlying.getString("frontendFragmentsUrls.mad"))
      mawFragment <- getBodyOf(config.underlying.getString("frontendFragmentsUrls.maw"))
    } yield {
      val doc = Jsoup.parse(muiFragment)
      val muiHead = doc.getElementsByTag("head").toString
      val muiBody = doc.getElementsByTag("body").toString

      Ok(views.html.index(muiHead, muiBody, madFragment, mawFragment))
    }

    response.recover {
      // TODO: add error handling
      case e: ConnectException =>
        logger.error(s"Error collecting frontend fragments ${e.getMessage()}")
    }

    response
  }

  private def getBodyOf(url: String): Future[String] = ws.url(url).get.map {
    response => {
      if (response.status == 200) {
        response.body
      } else {
        // Fallback is currently to ignore fragment if it is not displayed
        ""
      }
    }
  }
}
