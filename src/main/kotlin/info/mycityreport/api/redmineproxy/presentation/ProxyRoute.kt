package info.mycityreport.api.redmineproxy.presentation

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.request.uri
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.routing.routing

fun Route.redmineProxyRouting() {
    route("/{...}") {
        get {
            call.respondText(call.request.uri)
        }
    }
}

fun Application.registerRedmineProxyRoutes() {
    routing {
        redmineProxyRouting()
    }
}
