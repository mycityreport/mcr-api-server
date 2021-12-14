package info.mycityreport.api.redmineproxy.presentation

import info.mycityreport.api.redmineproxy.domain.entities.GETParameter
import info.mycityreport.api.redmineproxy.domain.entities.HTTPHeader
import info.mycityreport.api.redmineproxy.domain.entities.URLPath
import info.mycityreport.api.redmineproxy.usecase.GetProxyClient
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
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI

fun Route.redmineProxyRouting() {
    route("/proxy/{...}") {
        get {
            val getProxyClient by call.closestDI().instance<GetProxyClient>()
            val getProxyUseCase = GetProxyUseCase(getProxyClient)
            val rawPath = call.request.path().split("/").filterNot { it == "proxy" }.joinToString("/")
            val rawHeaders = call.request.headers.toMap()
            val rawParams = call.request.queryParameters.toMap()
            val path = URLPath(rawPath)
            val headers = rawHeaders.map { HTTPHeader(it.key, it.value) }
            val params = rawParams.map { GETParameter(it.key, it.value) }
            val response = getProxyUseCase.execute(path, headers, params)
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
