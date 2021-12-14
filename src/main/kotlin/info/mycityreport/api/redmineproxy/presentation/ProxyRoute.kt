package info.mycityreport.api.redmineproxy.presentation

import info.mycityreport.api.redmineproxy.infrastracture.KtorHTTPGetClient
import info.mycityreport.api.redmineproxy.usecase.GetProxyUseCase
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.request.path
import io.ktor.response.respondBytes
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.util.toMap

fun Route.redmineProxyRouting(baseURL: String) {
    val getProxyClient = KtorHTTPGetClient(baseURL)
    val getProxyUseCase = GetProxyUseCase(getProxyClient)

    route("/proxy/{...}") {
        get {
            val path = call.request.path().split("/").drop(2).joinToString("/")
            val headers = call.request.headers.toMap()
            val params = call.request.queryParameters.toMap()
            val response = getProxyUseCase.execute(path, headers, params)
            val contentType = ContentType.parse(response.contentType)
            val statusCode = HttpStatusCode.fromValue(response.statusCode)
            call.respondBytes(response.body, contentType, statusCode)
        }
    }
}

fun Application.registerRedmineProxyRoutes(baseURL: String) {
    routing {
        redmineProxyRouting(baseURL)
    }
}
