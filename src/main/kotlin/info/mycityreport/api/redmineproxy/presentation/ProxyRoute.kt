package info.mycityreport.api.redmineproxy.presentation

import info.mycityreport.api.redmineproxy.usecase.GetProxyClient
import info.mycityreport.api.redmineproxy.usecase.GetProxyUseCase
import info.mycityreport.api.redmineproxy.usecase.PostProxyClient
import info.mycityreport.api.redmineproxy.usecase.PostProxyUseCase
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.response.respondBytes
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import io.ktor.routing.routing
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI

fun Route.redmineProxyRouting() {
    route("/proxy/{...}") {
        get {
            val getProxyClient by call.closestDI().instance<GetProxyClient>()
            val getProxyUseCase = GetProxyUseCase(getProxyClient)
            val controller = GetProxyController(getProxyUseCase, call.request)
            val response = controller.callGetProxy()
            val contentType = ContentType.parse(response.contentType)
            val statusCode = HttpStatusCode.fromValue(response.statusCode)
            call.respondBytes(response.responseBody, contentType, statusCode)
        }
        post {
            val postProxyClient by call.closestDI().instance<PostProxyClient>()
            val postProxyUseCase = PostProxyUseCase(postProxyClient)
            val controller = PostProxyController(postProxyUseCase, call.request)
            val response = controller.callPostProxy()
            val contentType = ContentType.parse(response.contentType)
            val statusCode = HttpStatusCode.fromValue(response.statusCode)
            call.respondBytes(response.responseBody, contentType, statusCode)
        }
    }
}

fun Application.registerRedmineProxyRoutes() {
    routing {
        redmineProxyRouting()
    }
}
