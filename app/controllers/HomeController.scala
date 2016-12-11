package controllers

import java.net.ConnectException
import javax.inject._

import play.api.libs.ws._
import play.api.mvc._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.Configuration

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class HomeController @Inject()(ws: WSClient, executionContext: ExecutionContext, config: Configuration)  extends Controller {

  private type WSMessage = String

  private val logger = org.slf4j.LoggerFactory.getLogger(this.getClass)

  //  private implicit val logging = Logging(actorSystem.eventStream, logger.getName)

  def index: Action[AnyContent] = Action.async {
    val response = for {
      madFragment <- getBodyOf(config.underlying.getString("frontendFragmentsUrls.mad"))
      mawFragment <- getBodyOf(config.underlying.getString("frontendFragmentsUrls.maw"))
    } yield {
      // TODO: load html from filesystem
      Ok(views.html.index(madFragment, mawFragment))
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
        ""
      }
    }
  }
}
