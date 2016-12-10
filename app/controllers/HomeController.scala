package controllers

import java.net.ConnectException
import javax.inject._

import play.api.libs.ws._
import play.api.mvc._
import play.api.libs.concurrent.Execution.Implicits.defaultContext

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class HomeController @Inject()(ws: WSClient, executionContext: ExecutionContext) extends Controller {

  private type WSMessage = String

  private val logger = org.slf4j.LoggerFactory.getLogger(this.getClass)

  //  private implicit val logging = Logging(actorSystem.eventStream, logger.getName)

  def index: Action[AnyContent] = Action.async {
    var response = for {
      // TODO: get routes from config
      madFragment <- getBodyOf("http://localhost:3000/mad.fragment.html")
      mawFragment <- getBodyOf("http://localhost:3000/maw.fragment.html")
    } yield {
      // TODO: load html from filesystem
      Ok(views.html.index(madFragment, mawFragment))
    }

    response.recover {
      // TODO: add error handling
      case e: ConnectException =>
        logger.error(s"Ups something went very wrong: ${e.getMessage()}")
        response = Future { Ok("Ups an error") }
    }

    response
  }

  private def getBodyOf(url: String): Future[String] = ws.url(url).get.map(r => r.body)
}
