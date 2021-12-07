package info.mycityreport.api.healthcheck.presentation

import info.mycityreport.api.healthcheck.usecase.GetHealthStatusUseCase
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.routing.routing

fun Route.healthCheckRouting() {
    val useCase = GetHealthStatusUseCase()
    val getController = GetHealthCheckController(useCase)

    route("/health") {
        get {
            call.respond(getController.responseHealthCheckStatus())
        }
    }
}

fun Application.registerHealthCheckRoutes() {
    routing {
        healthCheckRouting()
    }
}
